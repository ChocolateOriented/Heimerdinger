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
@Table(name="macro_china_pmi_yearly")
@ApiModel(value = "MacroChinaPmiYearly", description = "中国 PMI 年率报告")
public class MacroChinaPmiYearly extends BaseTimeIndicator {
    @ApiModelProperty(value = "pmi", example = "1")
    private Double pmi;

    public static List<MacroChinaPmiYearly> parsListFromJson (String result) {
        JSONArray dataList = JSONArray.parseArray(result);
        Date begin = DateUtils.parseDate("2005-02-01");
        List<MacroChinaPmiYearly> list = new ArrayList<>(dataList.size());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begin);
        for (int i = 0; i < dataList.size(); i++) {
            Double pmi =  dataList.getDouble(i);
            MacroChinaPmiYearly obj = new MacroChinaPmiYearly();
            obj.setDate(calendar.getTime());
            obj.pmi = pmi;
            list.add(obj);
            calendar.add(Calendar.MONTH, 1);
        }
        return list;
    }

    public Double getPmi() {
        return pmi;
    }

    public void setPmi(Double pmi) {
        this.pmi = pmi;
    }
}
