package com.ruoyi.hemerdinger.finance.repository;

import com.ruoyi.hemerdinger.finance.domain.indicator.MacroChinaPmiYearly;
import com.ruoyi.hemerdinger.finance.domain.indicator.MacroChinaPpiYearly;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface MacroChinaPmiYearlyRepository extends CrudRepository<MacroChinaPmiYearly, Date> {
}
