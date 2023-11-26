package com.ruoyi.hemerdinger.finance.service;

import java.util.List;
import com.ruoyi.hemerdinger.finance.domain.StockPositionPlan;

/**
 * 股票持仓计划Service接口
 * 
 * @author lijingxiang
 * @date 2023-11-26
 */
public interface IStockPositionPlanService 
{
    /**
     * 查询股票持仓计划
     * 
     * @param id 股票持仓计划主键
     * @return 股票持仓计划
     */
    public StockPositionPlan selectStockPositionPlanById(Long id);

    /**
     * 查询股票持仓计划列表
     * 
     * @param stockPositionPlan 股票持仓计划
     * @return 股票持仓计划集合
     */
    public List<StockPositionPlan> selectStockPositionPlanList(StockPositionPlan stockPositionPlan);

    /**
     * 新增股票持仓计划
     * 
     * @param stockPositionPlan 股票持仓计划
     * @return 结果
     */
    public int insertStockPositionPlan(StockPositionPlan stockPositionPlan);

    /**
     * 修改股票持仓计划
     * 
     * @param stockPositionPlan 股票持仓计划
     * @return 结果
     */
    public int updateStockPositionPlan(StockPositionPlan stockPositionPlan);

    /**
     * 批量删除股票持仓计划
     * 
     * @param ids 需要删除的股票持仓计划主键集合
     * @return 结果
     */
    public int deleteStockPositionPlanByIds(Long[] ids);

    /**
     * 删除股票持仓计划信息
     * 
     * @param id 股票持仓计划主键
     * @return 结果
     */
    public int deleteStockPositionPlanById(Long id);

	void deleteStockPositionPlanByTraceId(Long traceId);
}
