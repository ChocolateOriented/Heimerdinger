package com.ruoyi.hemerdinger.finance.repository;

import com.ruoyi.hemerdinger.finance.domain.indicator.MacroChinaPpiYearly;
import com.ruoyi.hemerdinger.finance.domain.indicator.MacroChinaSupplyOfMoney;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface MacroChinaPpiYearlyRepository extends CrudRepository<MacroChinaPpiYearly, Date> {
}
