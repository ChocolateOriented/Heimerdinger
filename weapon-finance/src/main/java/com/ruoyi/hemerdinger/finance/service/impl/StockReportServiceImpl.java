package com.ruoyi.hemerdinger.finance.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.hemerdinger.finance.domain.StockDict;
import com.ruoyi.hemerdinger.finance.domain.StockReport;
import com.ruoyi.hemerdinger.finance.domain.vo.StockSubjectListVo;
import com.ruoyi.hemerdinger.finance.manager.AkShareManager;
import com.ruoyi.hemerdinger.finance.mapper.StockDictMapper;
import com.ruoyi.hemerdinger.finance.repository.StockReportRepository;
import com.ruoyi.hemerdinger.finance.service.IStockDictService;
import com.ruoyi.hemerdinger.finance.service.IStockReportService;
import com.ruoyi.hemerdinger.finance.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.common.utils.DateUtils.YYYY_MM_DD;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * 股票字典Service业务层处理
 *
 * @author lijingxiang
 * @date 2024-12-10
 */
@Service("stockReportService")
public class StockReportServiceImpl implements IStockReportService {
    @Autowired
    private StockDictMapper stockDictMapper;
    @Autowired
    private StockReportRepository stockReportRepository;
    @Autowired
    RestHighLevelClient client;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 更新经营评述
     */
    public void updateStockReport() {
        List<StockDict> stockDicts = stockDictMapper.selectStockDictList(new StockDict());
        if (CollectionUtils.isEmpty(stockDicts)){
            return;
        }

        for (int i = 0; i < stockDicts.size(); i++) {
            String url = "https://datacenter.eastmoney.com/securities/api/data/v1/get?reportName=RPT_F10_OP_BUSINESSANALYSIS&columns=SECUCODE%2CSECURITY_CODE%2CREPORT_DATE%2CBUSINESS_REVIEW&quoteColumns=&filter=(SECUCODE%3D%22{code}.{bourse}%22)&pageNumber=1&pageSize=10&sortTypes=&sortColumns=&source=HSF10&client=PC&v=039683721915947223";
            StockDict stockDict =  stockDicts.get(i);
            // 查询经营评述
            url = url.replace("{code}", stockDict.getCode());
            url = url.replace("{bourse}", stockDict.getBourse().toUpperCase());
            String s = HttpUtils.sendGet(url);
            JSONObject jsonObject = JSON.parseObject(s);
            JSONObject result = jsonObject.getJSONObject("result");
            if (result == null|| !result.containsKey("data")){
                continue;
            }
            JSONArray dataArray = result.getJSONArray("data");

            for (int j = 0; j < dataArray.size(); j++) {
                StockReport stockReport = dataArray.getObject(j, StockReport.class);
                // 判断是否已经存在
                String id = this.buildId(stockReport);
                // 通过id查询
                Optional<StockReport> byId = stockReportRepository.findById(id);
                if (!byId.isPresent()){
                    stockReport.setId(id);
                    stockReportRepository.save(stockReport);
                }
            }
        }
    }

    private static String buildId(StockReport stockReport) {
        LocalDate reportDate = stockReport.getREPORT_DATE();
        // 将LocalDate转成YYYY_MM_DD
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
        String dateStr =  reportDate.format(formatter);;
        return stockReport.getSECURITY_CODE() + "-" + dateStr;
    }

    /**
     * 查询相关概念股票
     * @param keyword
     * @return
     */
    @Override
    public List<StockSubjectListVo> list(String keyword) {
        // 构建查询条件
        MatchQueryBuilder matchQueryBuilder = matchQuery("BUSINESS_REVIEW", keyword);
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
            // 保留两位小数
            avg = Math.round(avg * 100) / 100.0;
            scoreMap.put(code, avg);
        }

        // 将scoreMap降序排列
        List<Map.Entry<String, Double>> entries = scoreMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        List<StockSubjectListVo> stockSubjectListVos = new ArrayList<>(entries.size());
        for (Map.Entry<String, Double> entry : entries) {
            String code = entry.getKey();
            Double score = entry.getValue();
            StockSubjectListVo stockSubjectListVo = new StockSubjectListVo();
            stockSubjectListVo.setCode(code);
            StockDict query = new StockDict();
            query.setCode(code);
            List<StockDict> stockDicts = stockDictMapper.selectStockDictList(query);
            if (!CollectionUtils.isEmpty(stockDicts)){
                stockSubjectListVo.setName(stockDicts.get(0).getName());
            }
            stockSubjectListVo.setScore(score);
            stockSubjectListVos.add(stockSubjectListVo);
        }
        return stockSubjectListVos;
    }

    @Override
    public List<StockReport> reportList(String code) {
        // 构建查询条件
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                //查询前1000条
                .withPageable(PageRequest.of(0, 1000))
                .withQuery(QueryBuilders.termQuery("SECURITY_CODE", code))
                .withSort(SortBuilders.fieldSort("REPORT_DATE").order(SortOrder.DESC))
                .build();
        SearchHits<StockReport> searchHits = elasticsearchRestTemplate.search(build, StockReport.class);
        List<SearchHit<StockReport>> searchHitList = searchHits.getSearchHits();
        return searchHitList.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
