package com.ruoyi.dev;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.hemerdinger.finance.domain.StockReport;
import com.ruoyi.hemerdinger.finance.manager.AkShareManager;
import com.ruoyi.hemerdinger.finance.service.impl.IndicatorServiceImpl;
import com.ruoyi.hemerdinger.finance.service.impl.StockReportServiceImpl;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@SpringBootTest(classes = RuoYiApplication.class)
@RunWith(SpringRunner.class)
public class Indicator {

    @Autowired
    IndicatorServiceImpl indicatorService;

    @Autowired
    StockReportServiceImpl stockReportService;

    @Autowired
    private AkShareManager akShareManager;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void saveOrUpdateIndicator(){
//        indicatorService.updateDaysIndicators();
        indicatorService.updateSupplyMoney();
    }
    @Test
    public void stockReportService(){
//        indicatorService.updateDaysIndicators();
        stockReportService.updateStockReport();
    }

    @Test
    public void stockReportEs(){
        // count 为数据的别名

        MatchQueryBuilder matchQueryBuilder = matchQuery("BUSINESS_REVIEW", "ASIC");
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                //查询前1000条
                .withPageable(org.springframework.data.domain.PageRequest.of(0, 1000))
                .withQuery(matchQueryBuilder).build();
        SearchHits<StockReport> searchHits = elasticsearchRestTemplate.search(build, StockReport.class);
        List<SearchHit<StockReport>> searchHitList = searchHits.getSearchHits();
        // 将结果以SECURITY_CODE分组统计, 算平均分值, 降序排列
        Map<String, List<SearchHit<StockReport>>> codeMap = new HashMap<>();
        searchHitList.forEach(searchHit -> {
            String SECURITY_CODE = searchHit.getContent().getSECURITY_CODE();
            List<SearchHit<StockReport>> hits = codeMap.getOrDefault(SECURITY_CODE, new ArrayList<>());
            hits.add(searchHit);
            codeMap.put(SECURITY_CODE, hits);
        });
        Map<String, Double> scoreMap = new HashMap<>();
        Set<String> codes = codeMap.keySet();
        for (String code : codes) {
            // 计算平均值
            List<SearchHit<StockReport>> stockReports = codeMap.get(code);
            double avg = stockReports.stream().mapToDouble(SearchHit::getScore).average().orElse(0);
            scoreMap.put(code, avg);
        }

        // 将scoreMap降序排列
        List<Map.Entry<String, Double>> entries = scoreMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());
        System.out.println("scoreMap:"+ entries);
    }

    @Test
    public void Interpolate(){
        List<JSONObject> data = Arrays.asList(
                new JSONObject() {{
                    put("date", "2024-01-01");
                    put("value1", 5.0);
                    put("value2", 10.0);
                }},
                new JSONObject() {{
                    put("date", "2024-05-03");
                    put("value1", 15.0);
                    put("value2", 20.0);
                }}
        );

        List<JSONObject> result = fillMissingDatesAndInterpolate(data);
        result.forEach(System.out::println);
    }

    /**
     * 填充日期并线性插值
     * @param data
     * @return
     */
    public static List<JSONObject> fillMissingDatesAndInterpolate(List<JSONObject> data) {
        if (data == null || data.isEmpty()) {
            return data;
        }

        // 按日期排序
        data.sort(Comparator.comparing(o -> ((String) o.get("date"))));

        LocalDate minDate = LocalDate.parse(((String) data.get(0).get("date")));
        LocalDate maxDate = LocalDate.parse(((String) data.get(data.size() - 1).get("date")));

        // 提取所有列名除了 'date'
        Set<String> columns = data.stream()
                .flatMap(map -> map.keySet().stream())
                .filter(key -> !"date".equals(key))
                .collect(Collectors.toSet());

        // Create a map for quick lookup of existing dates and their values
        Map<LocalDate, Map<String, Double>> valueMap = new HashMap<>();
        for (Map<String, Object> item : data) {
            LocalDate date = LocalDate.parse((String) item.get("date"));
            valueMap.put(date, item.entrySet().stream()
                    .filter(entry -> !"date".equals(entry.getKey()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> ((Number) entry.getValue()).doubleValue()
                    )));
        }

        List<JSONObject> filledData = new ArrayList<>();
        LocalDate currentDate = minDate;

        while (!currentDate.isAfter(maxDate)) {
            JSONObject entry = new JSONObject();
            entry.put("date", currentDate.toString());

            for (String col : columns) {
                Double v = interpolateColumn(col, valueMap, currentDate);
                // 转成String保留小数点后两位
                v = Double.parseDouble(String.format("%.2f", v));
                entry.put(col, v);
            }

            filledData.add(entry);
            currentDate = currentDate.plusDays(1);
        }

        return filledData;
    }

    private static Double interpolateColumn(String column, Map<LocalDate, Map<String, Double>> valueMap, LocalDate targetDate) {
        LocalDate prevDate = null;
        Double prevValue = null;
        LocalDate nextDate = null;
        Double nextValue = null;
        Set<LocalDate> localDates = valueMap.keySet();
        // 升序排列localDates
        List<LocalDate> localDatesList = localDates.stream().sorted().collect(Collectors.toList());
        // 查找具有实际值的前一项和后一项
        for (LocalDate date : localDatesList) {
            if (!date.isAfter(targetDate) && valueMap.get(date).containsKey(column)) {
                prevDate = date;
                prevValue = valueMap.get(date).get(column);
            }
            if (date.isAfter(targetDate) && valueMap.get(date).containsKey(column)) {
                nextDate = date;
                nextValue = valueMap.get(date).get(column);
                break; // Stop once we find the next item
            }
        }
        // 如果是前一个日期，就返回前一个值
        if (prevDate.isEqual(targetDate)){
            return prevValue;
        }

        if (prevDate == null || nextDate == null) {
            // 如果没有找到prev或next，就不能插入
            return null;
        }

        long daysDiff = ChronoUnit.DAYS.between(prevDate, nextDate);
        double valueDiff = nextValue - prevValue;
        double dailyIncrement = valueDiff / daysDiff;

        long daysFromPrev = ChronoUnit.DAYS.between(prevDate, targetDate);
        return prevValue + dailyIncrement * daysFromPrev;
    }
}
