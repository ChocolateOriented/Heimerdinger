package com.ruoyi.hemerdinger.finance.domain.indicator;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name="macro_stock_a_all_pb")
@ApiModel(value = "StockAAllPb", description = "股等权重中位数市净率")
public class StockAAllPb extends BaseTimeIndicator implements TimeIndicatorHandle<StockAAllPb> {
    @ApiModelProperty(value = "全部A股市净率中位数", example = "1")
    private Double middlePB;

    @ApiModelProperty(value = "全部A股市净率等权平均", example = "1")
    private Double equalWeightAveragePB;

    @ApiModelProperty(value = "当前市净率中位数在最近10年数据上的分位数", example = "1")
    private Double quantileInRecent10YearsMiddlePB;

    @ApiModelProperty(value = "当前市净率等权平均在最近10年数据上的分位数", example = "1")
    private Double quantileInRecent10YearsEqualWeightAveragePB;

    @Override
    public StockAAllPb parsFromJson(String str) {
        JSONObject json = JSONObject.parseObject(str);
        return json.toJavaObject(StockAAllPb.class);
    }

    public Double getMiddlePB() {
        return middlePB;
    }

    public void setMiddlePB(Double middlePB) {
        this.middlePB = middlePB;
    }

    public Double getEqualWeightAveragePB() {
        return equalWeightAveragePB;
    }

    public void setEqualWeightAveragePB(Double equalWeightAveragePB) {
        this.equalWeightAveragePB = equalWeightAveragePB;
    }

    public Double getQuantileInRecent10YearsMiddlePB() {
        return quantileInRecent10YearsMiddlePB;
    }

    public void setQuantileInRecent10YearsMiddlePB(Double quantileInRecent10YearsMiddlePB) {
        this.quantileInRecent10YearsMiddlePB = quantileInRecent10YearsMiddlePB;
    }

    public Double getQuantileInRecent10YearsEqualWeightAveragePB() {
        return quantileInRecent10YearsEqualWeightAveragePB;
    }

    public void setQuantileInRecent10YearsEqualWeightAveragePB(Double quantileInRecent10YearsEqualWeightAveragePB) {
        this.quantileInRecent10YearsEqualWeightAveragePB = quantileInRecent10YearsEqualWeightAveragePB;
    }

    @Override
    public String toString() {
        return "StockAAllPb{" +
                ", middlePB=" + middlePB +
                ", equalWeightAveragePB=" + equalWeightAveragePB +
                ", quantileInRecent10YearsMiddlePB=" + quantileInRecent10YearsMiddlePB +
                ", quantileInRecent10YearsEqualWeightAveragePB=" + quantileInRecent10YearsEqualWeightAveragePB +
                '}';
    }
}
