package com.ruoyi.hemerdinger.finance.repository;

import com.ruoyi.hemerdinger.finance.domain.StockReport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StockReportRepository extends ElasticsearchRepository<StockReport, String> {

}
