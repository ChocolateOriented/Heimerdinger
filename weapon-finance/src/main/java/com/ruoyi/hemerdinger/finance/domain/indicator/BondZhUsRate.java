package com.ruoyi.hemerdinger.finance.domain.indicator;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="macro_bond_zh_us_rate")
@ApiModel(value = "BondZhUsRate", description = "中美国债收益率")
public class BondZhUsRate extends BaseTimeIndicator implements TimeIndicatorHandle<BondZhUsRate> {
    @ApiModelProperty(value = "中国国债收益率2年", example = "1")
    private Double zh_bond_rate_2year;

    @ApiModelProperty(value = "中国国债收益率5年", example = "1")
    private Double zh_bond_rate_5year;

    @ApiModelProperty(value = "中国国债收益率10年", example = "1")
    private Double zh_bond_rate_10year;

    @ApiModelProperty(value = "中国国债收益率30年", example = "1")
    private Double zh_bond_rate_30year;

    @ApiModelProperty(value = "中国国债收益率10年-2年", example = "1")
    private Double zh_bond_rate_10_2year;

    @ApiModelProperty(value = "中国GDP年增率", example = "1")
    private Double zh_gdp_growth_rate;

    @ApiModelProperty(value = "美国国债收益率2年", example = "1")
    private Double us_bond_rate_2year;

    @ApiModelProperty(value = "美国国债收益率5年", example = "1")
    private Double us_bond_rate_5year;

    @ApiModelProperty(value = "美国国债收益率10年", example = "1")
    private Double us_bond_rate_10year;
    @ApiModelProperty(value = "美国国债收益率30年", example = "1")
    private Double us_bond_rate_30year;
    @ApiModelProperty(value = "美国国债收益率10年-2年", example = "1")
    private Double us_bond_rate_10_2year;
    @ApiModelProperty(value = "美国GDP年增率", example = "1")
    private Double us_gdp_growth_rate;

    @Override
    public BondZhUsRate parsFromJson (String str) {
        JSONObject json = JSONObject.parseObject(str);
        BondZhUsRate bondZhUsRate = new BondZhUsRate();
        bondZhUsRate.setDate(json.getDate("日期"));
        bondZhUsRate.zh_bond_rate_2year = json.getDouble("中国国债收益率2年");
        bondZhUsRate.zh_bond_rate_5year = json.getDouble("中国国债收益率5年");
        bondZhUsRate.zh_bond_rate_10year = json.getDouble("中国国债收益率10年");
        bondZhUsRate.zh_bond_rate_30year = json.getDouble("中国国债收益率30年");
        bondZhUsRate.zh_bond_rate_10_2year = json.getDouble("中国国债收益率10年-2年");
        bondZhUsRate.zh_gdp_growth_rate = json.getDouble("中国GDP年增率");
        bondZhUsRate.us_bond_rate_2year = json.getDouble("美国国债收益率2年");
        bondZhUsRate.us_bond_rate_5year = json.getDouble("美国国债收益率5年");
        bondZhUsRate.us_bond_rate_10year = json.getDouble("美国国债收益率10年");
        bondZhUsRate.us_bond_rate_30year = json.getDouble("美国国债收益率30年");
        bondZhUsRate.us_bond_rate_10_2year = json.getDouble("美国国债收益率10年-2年");
        bondZhUsRate.us_gdp_growth_rate = json.getDouble("美国GDP年增率");
        return bondZhUsRate;
    }

    public Double getZh_bond_rate_2year() {
        return zh_bond_rate_2year;
    }

    public void setZh_bond_rate_2year(Double zh_bond_rate_2year) {
        this.zh_bond_rate_2year = zh_bond_rate_2year;
    }

    public Double getZh_bond_rate_5year() {
        return zh_bond_rate_5year;
    }

    public void setZh_bond_rate_5year(Double zh_bond_rate_5year) {
        this.zh_bond_rate_5year = zh_bond_rate_5year;
    }

    public Double getZh_bond_rate_10year() {
        return zh_bond_rate_10year;
    }

    public void setZh_bond_rate_10year(Double zh_bond_rate_10year) {
        this.zh_bond_rate_10year = zh_bond_rate_10year;
    }

    public Double getZh_bond_rate_30year() {
        return zh_bond_rate_30year;
    }

    public void setZh_bond_rate_30year(Double zh_bond_rate_30year) {
        this.zh_bond_rate_30year = zh_bond_rate_30year;
    }

    public Double getZh_bond_rate_10_2year() {
        return zh_bond_rate_10_2year;
    }

    public void setZh_bond_rate_10_2year(Double zh_bond_rate_10_2year) {
        this.zh_bond_rate_10_2year = zh_bond_rate_10_2year;
    }

    public Double getZh_gdp_growth_rate() {
        return zh_gdp_growth_rate;
    }

    public void setZh_gdp_growth_rate(Double zh_gdp_growth_rate) {
        this.zh_gdp_growth_rate = zh_gdp_growth_rate;
    }

    public Double getUs_bond_rate_2year() {
        return us_bond_rate_2year;
    }

    public void setUs_bond_rate_2year(Double us_bond_rate_2year) {
        this.us_bond_rate_2year = us_bond_rate_2year;
    }

    public Double getUs_bond_rate_5year() {
        return us_bond_rate_5year;
    }

    public void setUs_bond_rate_5year(Double us_bond_rate_5year) {
        this.us_bond_rate_5year = us_bond_rate_5year;
    }

    public Double getUs_bond_rate_10year() {
        return us_bond_rate_10year;
    }

    public void setUs_bond_rate_10year(Double us_bond_rate_10year) {
        this.us_bond_rate_10year = us_bond_rate_10year;
    }

    public Double getUs_bond_rate_30year() {
        return us_bond_rate_30year;
    }

    public void setUs_bond_rate_30year(Double us_bond_rate_30year) {
        this.us_bond_rate_30year = us_bond_rate_30year;
    }

    public Double getUs_bond_rate_10_2year() {
        return us_bond_rate_10_2year;
    }

    public void setUs_bond_rate_10_2year(Double us_bond_rate_10_2year) {
        this.us_bond_rate_10_2year = us_bond_rate_10_2year;
    }

    public Double getUs_gdp_growth_rate() {
        return us_gdp_growth_rate;
    }

    public void setUs_gdp_growth_rate(Double us_gdp_growth_rate) {
        this.us_gdp_growth_rate = us_gdp_growth_rate;
    }
}
