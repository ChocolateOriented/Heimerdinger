package com.ruoyi.hemerdinger.finance.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtils {

    /**
     * 填充日期并线性插值
     *
     * @param data
     * @return
     */
    public static List<JSONObject> fillMissingDatesAndInterpolate(List<JSONObject> data, List<String> columns) {
        if (data == null || data.isEmpty()) {
            return data;
        }
        // 剔除date为空的数据
        data.removeIf(item -> item.get("date") == null);
        LocalDate minDate = LocalDate.parse(((String) data.get(0).get("date")));
        LocalDate maxDate = LocalDate.parse(((String) data.get(data.size() - 1).get("date")));

        // 为每一列创建创建用于快速查找现有日期及其值的映射
        Map<LocalDate, JSONObject> valueMap = new HashMap<>();
        for (JSONObject item : data) {
            LocalDate date = LocalDate.parse((String) item.get("date"));
            valueMap.put(date, item);
        }

        List<JSONObject> filledData = new ArrayList<>();
        LocalDate currentDate = minDate;

        HashMap<String, List<LocalDate>> nullCloumnDates = new HashMap<>();
        HashMap<String, Double> cloumnLastValue = new HashMap<>();
        // 如果有空缺日期进行填充
        while (currentDate.isBefore(maxDate)) {
            JSONObject entry;
            if (!valueMap.containsKey(currentDate)) {
                // 如果当前日期没有值，则创建一个空的JSON对象，用于插值
                entry = new JSONObject();
                entry.put("date", currentDate.toString());
                valueMap.put(currentDate, entry);
                filledData.add(entry);
            } else {
                entry = valueMap.get(currentDate);
                filledData.add(entry);
            }

            // 记录每一列空缺值两端的日期以及两端的值
            for (String col : columns) {
                if (col.equals("中国国债收益率10年")){
                    System.out.println("中国国债收益率10年");
                }
                Double value = null;
                if (entry.containsKey(col)) {
                    value = entry.getDouble(col);
                }
                // 如果是空值，进行记录
                if (null == value) {
                    List<LocalDate> nullDates = null;
                    if (!nullCloumnDates.containsKey(col)) {
                        nullDates = new ArrayList<>();
                        nullCloumnDates.put(col, nullDates);
                    }else {
                        nullDates = nullCloumnDates.get(col);
                    }
                    nullDates.add(currentDate);
                    continue;
                }
                // 如果是非空值, 检查空值列表及上一个有效值, 进行插值
                Double lastValue = null;
                if (cloumnLastValue.containsKey(col)) {
                    lastValue = cloumnLastValue.get(col);
                }
                // 如果没有上一个有效值，记录当前值, 且清空列表
                if (null == lastValue) {
                    cloumnLastValue.put(col, value);
                    nullCloumnDates.put(col, new ArrayList<>());
                    continue;
                }
                // 如果上一个有效值存在，则进行插值
                // 获取当前列的空缺日期列表
                List<LocalDate> nullDates = nullCloumnDates.get(col);
                // 如果当前列没有空缺日期，则直接跳过
                if (CollectionUtils.isEmpty(nullDates)) {
                    cloumnLastValue.put(col, value);
                    continue;
                }

                // 进行线性插值
                Integer dateSize = nullDates.size();
                Double valueSpan = (value - lastValue) / (dateSize + 1);
                for (int i = 0; i < nullDates.size(); i++) {
                    LocalDate localDate = nullDates.get(i);
                    JSONObject jsonObject = valueMap.get(localDate);
                    int j = i + 1;
                    Double v = lastValue + j * valueSpan;
                    jsonObject.put(col, v);
                }
                cloumnLastValue.put(col, value);
                nullCloumnDates.put(col, new ArrayList<>());
            }
            currentDate = currentDate.plusDays(1);
        }
        return filledData;
    }

}
