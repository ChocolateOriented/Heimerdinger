package com.ruoyi.hemerdinger.finance.domain.indicator;


import com.alibaba.fastjson.JSONArray;
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
@Table(name="macro_china_ppi_yearly")
@ApiModel(value = "MacroChinaPpiYearly", description = "中国 PPI 年率报告")
public class MacroChinaPpiYearly extends BaseTimeIndicator {
    @ApiModelProperty(value = "ppi", example = "1")
    private Double ppi;

    public static List<MacroChinaPpiYearly> parsListFromJson (String result) {
        JSONArray dataList = JSONArray.parseArray(result);
        Date begin = DateUtils.parseDate("1995-08-01");
        List<MacroChinaPpiYearly> list = new ArrayList<>(dataList.size());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begin);
        for (int i = 0; i < dataList.size(); i++) {
            Double ppi =  dataList.getDouble(i);
            MacroChinaPpiYearly obj = new MacroChinaPpiYearly();
            obj.setDate(calendar.getTime());
            obj.ppi = ppi;
            list.add(obj);
            calendar.add(Calendar.MONTH, 1);
        }
        return list;
    }

    public Double getPpi() {
        return ppi;
    }

    public void setPpi(Double ppi) {
        this.ppi = ppi;
    }
}
