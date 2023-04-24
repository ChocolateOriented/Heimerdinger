package com.ruoyi.hemerdinger.finance.domain.indicator;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="macro_stock_a_ttm_lyr")
@ApiModel(value = "StockATtmLyr", description = "A 股等权重与中位数市盈率")
public class StockATtmLyr extends BaseTimeIndicator implements TimeIndicatorHandle<StockATtmLyr> {
    @ApiModelProperty(value = "全A股滚动市盈率(TTM)中位数", example = "1")
    private Double middlePETTM;

    @ApiModelProperty(value = "全A股滚动市盈率(TTM)等权平均", example = "1")
    private Double averagePETTM;

    @ApiModelProperty(value = "全A股静态市盈率(LYR)中位数", example = "1")
    private Double middlePELYR;

    @ApiModelProperty(value = "全A股静态市盈率(LYR)等权平均", example = "1")
    private Double averagePELYR;

    @ApiModelProperty(value = "当前TTM(滚动市盈率)中位数在最近10年数据上的分位数", example = "1")
    private Double quantileInRecent10YearsMiddlePeTtm;

    @ApiModelProperty(value = "当前\"TTM(滚动市盈率)等权平均\"在在最近10年数据上的分位数", example = "1")
    private Double quantileInRecent10YearsAveragePeTtm;

    @ApiModelProperty(value = "当前\"LYR(静态市盈率)中位数\"在最近10年数据上的分位数", example = "1")
    private Double quantileInRecent10YearsMiddlePeLyr;

    @ApiModelProperty(value = "当前\"LYR(静态市盈率)等权平均\"在最近10年数据上的分位数", example = "1")
    private Double quantileInRecent10YearsAveragePeLyr;


    @Override
    public StockATtmLyr parsFromJson(String str) {
        JSONObject json = JSONObject.parseObject(str);
        return json.toJavaObject(StockATtmLyr.class);
    }

    public Double getMiddlePETTM() {
        return middlePETTM;
    }

    public void setMiddlePETTM(Double middlePETTM) {
        this.middlePETTM = middlePETTM;
    }

    public Double getAveragePETTM() {
        return averagePETTM;
    }

    public void setAveragePETTM(Double averagePETTM) {
        this.averagePETTM = averagePETTM;
    }

    public Double getMiddlePELYR() {
        return middlePELYR;
    }

    public void setMiddlePELYR(Double middlePELYR) {
        this.middlePELYR = middlePELYR;
    }

    public Double getAveragePELYR() {
        return averagePELYR;
    }

    public void setAveragePELYR(Double averagePELYR) {
        this.averagePELYR = averagePELYR;
    }

    public Double getQuantileInRecent10YearsMiddlePeTtm() {
        return quantileInRecent10YearsMiddlePeTtm;
    }

    public void setQuantileInRecent10YearsMiddlePeTtm(Double quantileInRecent10YearsMiddlePeTtm) {
        this.quantileInRecent10YearsMiddlePeTtm = quantileInRecent10YearsMiddlePeTtm;
    }

    public Double getQuantileInRecent10YearsAveragePeTtm() {
        return quantileInRecent10YearsAveragePeTtm;
    }

    public void setQuantileInRecent10YearsAveragePeTtm(Double quantileInRecent10YearsAveragePeTtm) {
        this.quantileInRecent10YearsAveragePeTtm = quantileInRecent10YearsAveragePeTtm;
    }

    public Double getQuantileInRecent10YearsMiddlePeLyr() {
        return quantileInRecent10YearsMiddlePeLyr;
    }

    public void setQuantileInRecent10YearsMiddlePeLyr(Double quantileInRecent10YearsMiddlePeLyr) {
        this.quantileInRecent10YearsMiddlePeLyr = quantileInRecent10YearsMiddlePeLyr;
    }

    public Double getQuantileInRecent10YearsAveragePeLyr() {
        return quantileInRecent10YearsAveragePeLyr;
    }

    public void setQuantileInRecent10YearsAveragePeLyr(Double quantileInRecent10YearsAveragePeLyr) {
        this.quantileInRecent10YearsAveragePeLyr = quantileInRecent10YearsAveragePeLyr;
    }
}
