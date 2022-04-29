package com.ruoyi.hemerdinger.finance.domain.vo;

import java.math.BigDecimal;

/**
 * @File: tradeGradeVo
 * @Version: 1.0
 * @Description:
 * @Author: lijingxiang
 * @Date: 2022/4/28 18:02
 */
public class TradeGradeVo {
	private Long traceId;
	private String name;
	private BigDecimal currentPrice;
	private BigDecimal planRise;
	private BigDecimal planFall;
	private BigDecimal grade;

	public TradeGradeVo() {
	}

	public TradeGradeVo(Long traceId, String name, BigDecimal currentPrice, BigDecimal planRise, BigDecimal planFall, BigDecimal grade) {
		this.traceId = traceId;
		this.name = name;
		this.currentPrice = currentPrice;
		this.planRise = planRise;
		this.planFall = planFall;
		this.grade = grade;
	}

	public Long getTraceId() {
		return traceId;
	}

	public void setTraceId(Long traceId) {
		this.traceId = traceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public BigDecimal getPlanRise() {
		return planRise;
	}

	public void setPlanRise(BigDecimal planRise) {
		this.planRise = planRise;
	}

	public BigDecimal getPlanFall() {
		return planFall;
	}

	public void setPlanFall(BigDecimal planFall) {
		this.planFall = planFall;
	}

	public BigDecimal getGrade() {
		return grade;
	}

	public void setGrade(BigDecimal grade) {
		this.grade = grade;
	}
}
