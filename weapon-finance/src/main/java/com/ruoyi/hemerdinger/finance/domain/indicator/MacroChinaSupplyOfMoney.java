package com.ruoyi.hemerdinger.finance.domain.indicator;


import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="macro_china_supply_of_money")
@ApiModel(value = "BondZhUsRate", description = "中美国债收益率")
public class MacroChinaSupplyOfMoney extends BaseTimeIndicator implements TimeIndicatorHandle<MacroChinaSupplyOfMoney> {
    @ApiModelProperty(value = "货币和准货币（广义货币M2）", example = "1")
    private Double m2;

    @ApiModelProperty(value = "货币和准货币（广义货币M2）同比增长", example = "1")
    private Double m2Growth;

    @ApiModelProperty(value = "货币(狭义货币M1)", example = "1")
    private Double m1;

    @ApiModelProperty(value = "货币(狭义货币M1)同比增长", example = "1")
    private Double m1Growth;

    @ApiModelProperty(value = "流通中现金(M0)", example = "1")
    private Double m0;

    @ApiModelProperty(value = "流通中现金(M0)同比增长", example = "1")
    private Double m0Growth;

    @ApiModelProperty(value = "活期存款", example = "1")
    private Double current_deposit;

    @ApiModelProperty(value = "活期存款同比增长", example = "1")
    private Double currentDepositGrowth;

    @ApiModelProperty(value = "准货币", example = "1")
    private Double quasiMoney;

    @ApiModelProperty(value = "准货币同比增长", example = "1")
    private Double quasiMoneyGrowth;

    @ApiModelProperty(value = "定期存款", example = "1")
    private Double fixedTermDeposit;

    @ApiModelProperty(value = "定期存款同比增长", example = "1")
    private Double fixedTermDepositGrowth;

    @ApiModelProperty(value = "储蓄存款", example = "1")
    private Double savingDeposits;

    @ApiModelProperty(value = "储蓄存款同比增长", example = "1")
    private Double savingDepositsGrowth;

    @ApiModelProperty(value = "其他存款", example = "1")
    private Double otherDeposits;

    @ApiModelProperty(value = "其他存款同比增长", example = "1")
    private Double otherDepositsGrowth;

    @Override
    public MacroChinaSupplyOfMoney parsFromJson (String str) {
        JSONObject json = JSONObject.parseObject(str);
        MacroChinaSupplyOfMoney obj = new MacroChinaSupplyOfMoney();
        obj.setDate(DateUtils.dateTime("yyyy.MM", json.getString("统计时间")));
        obj.m2 = json.getDouble("货币和准货币（广义货币M2）");
        obj.m2Growth = json.getDouble("货币和准货币（广义货币M2）同比增长");;
        obj.m1 = json.getDouble("货币(狭义货币M1)");;
        obj.m1Growth = json.getDouble("货币(狭义货币M1)同比增长");;
        obj.m0 = json.getDouble("流通中现金(M0)");;
        obj.m0Growth = json.getDouble("流通中现金(M0)同比增长");;
        obj.current_deposit = json.getDouble("活期存款");;
        obj.currentDepositGrowth = json.getDouble("活期存款同比增长");;
        obj.quasiMoney = json.getDouble("准货币");;
        obj.quasiMoneyGrowth = json.getDouble("准货币同比增长");;
        obj.fixedTermDeposit = json.getDouble("定期存款");;
        obj.fixedTermDepositGrowth = json.getDouble("定期存款同比增长");;
        obj.savingDeposits = json.getDouble("储蓄存款");;
        obj.savingDepositsGrowth = json.getDouble("储蓄存款同比增长");;
        obj.otherDeposits = json.getDouble("其他存款");;
        obj.otherDepositsGrowth = json.getDouble("其他存款同比增长");;
        return obj;
    }

    public Double getM2() {
        return m2;
    }

    public void setM2(Double m2) {
        this.m2 = m2;
    }

    public Double getM2Growth() {
        return m2Growth;
    }

    public void setM2Growth(Double m2Growth) {
        this.m2Growth = m2Growth;
    }

    public Double getM1() {
        return m1;
    }

    public void setM1(Double m1) {
        this.m1 = m1;
    }

    public Double getM1Growth() {
        return m1Growth;
    }

    public void setM1Growth(Double m1Growth) {
        this.m1Growth = m1Growth;
    }

    public Double getM0() {
        return m0;
    }

    public void setM0(Double m0) {
        this.m0 = m0;
    }

    public Double getM0Growth() {
        return m0Growth;
    }

    public void setM0Growth(Double m0Growth) {
        this.m0Growth = m0Growth;
    }

    public Double getCurrent_deposit() {
        return current_deposit;
    }

    public void setCurrent_deposit(Double current_deposit) {
        this.current_deposit = current_deposit;
    }

    public Double getCurrentDepositGrowth() {
        return currentDepositGrowth;
    }

    public void setCurrentDepositGrowth(Double currentDepositGrowth) {
        this.currentDepositGrowth = currentDepositGrowth;
    }

    public Double getQuasiMoney() {
        return quasiMoney;
    }

    public void setQuasiMoney(Double quasiMoney) {
        this.quasiMoney = quasiMoney;
    }

    public Double getQuasiMoneyGrowth() {
        return quasiMoneyGrowth;
    }

    public void setQuasiMoneyGrowth(Double quasiMoneyGrowth) {
        this.quasiMoneyGrowth = quasiMoneyGrowth;
    }

    public Double getFixedTermDeposit() {
        return fixedTermDeposit;
    }

    public void setFixedTermDeposit(Double fixedTermDeposit) {
        this.fixedTermDeposit = fixedTermDeposit;
    }

    public Double getFixedTermDepositGrowth() {
        return fixedTermDepositGrowth;
    }

    public void setFixedTermDepositGrowth(Double fixedTermDepositGrowth) {
        this.fixedTermDepositGrowth = fixedTermDepositGrowth;
    }

    public Double getSavingDeposits() {
        return savingDeposits;
    }

    public void setSavingDeposits(Double savingDeposits) {
        this.savingDeposits = savingDeposits;
    }

    public Double getSavingDepositsGrowth() {
        return savingDepositsGrowth;
    }

    public void setSavingDepositsGrowth(Double savingDepositsGrowth) {
        this.savingDepositsGrowth = savingDepositsGrowth;
    }

    public Double getOtherDeposits() {
        return otherDeposits;
    }

    public void setOtherDeposits(Double otherDeposits) {
        this.otherDeposits = otherDeposits;
    }

    public Double getOtherDepositsGrowth() {
        return otherDepositsGrowth;
    }

    public void setOtherDepositsGrowth(Double otherDepositsGrowth) {
        this.otherDepositsGrowth = otherDepositsGrowth;
    }
}
