package com.ruoyi.hemerdinger.finance.domain.vo;

import java.math.BigDecimal;

/**
 * @File: TradeAdviceListVo
 * @Version: 1.0
 * @Description:
 * @Author: lijingxiang
 * @Date: 2022/4/28 17:18
 */
public class TradeAdviceListVo {

	private Long traceId;
	private String name;
	private BigDecimal currentPrice;
	private TradeAdviceType tradeAdviceType;
	private BigDecimal adviceAmount;
	private BigDecimal realityAmount;

	public TradeAdviceListVo() {
	}

	public TradeAdviceListVo(Long traceId, String name, BigDecimal currentPrice, TradeAdviceType tradeAdviceType, BigDecimal adviceAmount,
			BigDecimal realityAmount) {
		this.traceId = traceId;
		this.name = name;
		this.currentPrice = currentPrice;
		this.tradeAdviceType = tradeAdviceType;
		this.adviceAmount = adviceAmount;
		this.realityAmount = realityAmount;
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

	public TradeAdviceType getTradeAdviceType() {
		return tradeAdviceType;
	}

	public void setTradeAdviceType(TradeAdviceType tradeAdviceType) {
		this.tradeAdviceType = tradeAdviceType;
	}

	public BigDecimal getAdviceAmount() {
		return adviceAmount;
	}

	public void setAdviceAmount(BigDecimal adviceAmount) {
		this.adviceAmount = adviceAmount;
	}

	public BigDecimal getRealityAmount() {
		return realityAmount;
	}

	public void setRealityAmount(BigDecimal realityAmount) {
		this.realityAmount = realityAmount;
	}
}
