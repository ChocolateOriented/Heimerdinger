package com.ruoyi.hemerdinger.finance.domain.indicator;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="macro_china_cpi_yearly")
@ApiModel(value = "MacroChinaCpiYearly", description = "中国 CPI 年率报告")
public class MacroChinaCpiYearly extends BaseTimeIndicator implements TimeIndicatorHandle<MacroChinaCpiYearly>{
    @ApiModelProperty(value = "cpi", example = "1")
    private Double cpi;

    @Override
    public MacroChinaCpiYearly parsFromJson(String str) {
        JSONObject json = JSONObject.parseObject(str);
        MacroChinaCpiYearly obj = new MacroChinaCpiYearly();
//        {"date":"1986-02-01T00:00:00.000","value":7.1}
        obj.setDate(json.getDate("date"));
        obj.cpi = json.getDouble("value");
        return obj;
    }

    public Double getCpi() {
        return cpi;
    }

    public void setCpi(Double cpi) {
        this.cpi = cpi;
    }

}
