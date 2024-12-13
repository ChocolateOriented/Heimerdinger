package com.ruoyi.hemerdinger.finance.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class DataUtils {

    /**
     * 填充日期并线性插值
     * @param data
     * @return
     */
    public static List<JSONObject> fillMissingDatesAndInterpolate(List<JSONObject> data, List<String> columns) {
        if (data == null || data.isEmpty()) {
            return data;
        }
        // 剔除date为空的数据
        data.removeIf(item -> item.get("date") == null);
        // 按日期排序
        data.sort(Comparator.comparing(o -> ((String) o.get("date"))));

        LocalDate minDate = LocalDate.parse(((String) data.get(0).get("date")));
        LocalDate maxDate = LocalDate.parse(((String) data.get(data.size() - 1).get("date")));

        // 为每一列创建创建用于快速查找现有日期及其值的映射
        Map<LocalDate, JSONObject> valueMap = new HashMap<>();
        for (JSONObject item : data) {
            JSONObject valueJ = new JSONObject();
            LocalDate date = LocalDate.parse((String) item.get("date"));

            for (String col : columns) {
                String value = item.getString(col);
                if (value == null || !NumberUtils.isCreatable(value)) {
                    continue;
                }
                valueJ.put( col, Double.parseDouble(value));
            }
            valueMap.put(date, valueJ);
        }

        List<JSONObject> filledData = new ArrayList<>();
        LocalDate currentDate = minDate;

        while (!currentDate.isAfter(maxDate)) {
            JSONObject entry = new JSONObject();
            entry.put("date", currentDate.toString());

            for (String col : columns) {
                Double v = interpolateColumn(col, valueMap, currentDate);
                if (v == null){
                    entry.put(col, null);
                    continue;
                }
                // 转成String保留小数点后两位
                v = Double.parseDouble(String.format("%.2f", v));
                entry.put(col, v);
            }

            filledData.add(entry);
            currentDate = currentDate.plusDays(1);
        }

        return filledData;
    }

    /**
     * 线性插值
     * @param column
     * @param valueMap
     * @param targetDate
     * @return
     */
    private static Double interpolateColumn(String column, Map<LocalDate, JSONObject> valueMap, LocalDate targetDate) {
        LocalDate prevDate = null;
        Double prevValue = null;
        LocalDate nextDate = null;
        Double nextValue = null;
        Set<LocalDate> localDates = valueMap.keySet();
        // 升序排列localDates
        List<LocalDate> localDatesList = localDates.stream().sorted().collect(Collectors.toList());
        // 查找具有实际值前一项和后一项
        for (LocalDate date : localDatesList) {
            JSONObject jsonObject = valueMap.get(date);
            if (!jsonObject.containsKey(column)){
                continue;
            }
            Double v = jsonObject.getDouble(column);
            if (v == null){
                continue;
            }
            if (!date.isAfter(targetDate) && jsonObject.containsKey(column)) {
                // 如果
                prevDate = date;
                prevValue = v;
            }
            if (date.isAfter(targetDate) && jsonObject.containsKey(column)) {
                nextDate = date;
                nextValue = v;
                break; // Stop once we find the next item
            }
        }

        if (prevDate == null || nextDate == null) {
            // 如果没有找到prev或next，就不能插入
            return null;
        }

        // 如果是前一个日期，就返回前一个值
        if (prevDate.isEqual(targetDate)){
            return prevValue;
        }

        long daysDiff = ChronoUnit.DAYS.between(prevDate, nextDate);
        double valueDiff = nextValue - prevValue;
        double dailyIncrement = valueDiff / daysDiff;

        long daysFromPrev = ChronoUnit.DAYS.between(prevDate, targetDate);
        return prevValue + dailyIncrement * daysFromPrev;
    }
}
