package com.ruoyi.hemerdinger.finance.repository;

import com.ruoyi.hemerdinger.finance.domain.indicator.StockAAllPb;
import com.ruoyi.hemerdinger.finance.domain.indicator.StockATtmLyr;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface StockATtmLyrRepository extends CrudRepository<StockATtmLyr, Date> {
}
