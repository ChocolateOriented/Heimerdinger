package com.ruoyi.hemerdinger.finance.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hemerdinger.finance.mapper.StockPositionPlanMapper;
import com.ruoyi.hemerdinger.finance.domain.StockPositionPlan;
import com.ruoyi.hemerdinger.finance.service.IStockPositionPlanService;

/**
 * 股票持仓计划Service业务层处理
 * 
 * @author lijingxiang
 * @date 2023-11-26
 */
@Service
public class StockPositionPlanServiceImpl implements IStockPositionPlanService 
{
    @Autowired
    private StockPositionPlanMapper stockPositionPlanMapper;

    /**
     * 查询股票持仓计划
     * 
     * @param id 股票持仓计划主键
     * @return 股票持仓计划
     */
    @Override
    public StockPositionPlan selectStockPositionPlanById(Long id)
    {
        return stockPositionPlanMapper.selectStockPositionPlanById(id);
    }

    /**
     * 查询股票持仓计划列表
     * 
     * @param stockPositionPlan 股票持仓计划
     * @return 股票持仓计划
     */
    @Override
    public List<StockPositionPlan> selectStockPositionPlanList(StockPositionPlan stockPositionPlan)
    {
        return stockPositionPlanMapper.selectStockPositionPlanList(stockPositionPlan);
    }

    /**
     * 新增股票持仓计划
     * 
     * @param stockPositionPlan 股票持仓计划
     * @return 结果
     */
    @Override
    public int insertStockPositionPlan(StockPositionPlan stockPositionPlan)
    {
        stockPositionPlan.setCreateTime(DateUtils.getNowDate());
        return stockPositionPlanMapper.insertStockPositionPlan(stockPositionPlan);
    }

    /**
     * 修改股票持仓计划
     * 
     * @param stockPositionPlan 股票持仓计划
     * @return 结果
     */
    @Override
    public int updateStockPositionPlan(StockPositionPlan stockPositionPlan)
    {
        stockPositionPlan.setUpdateTime(DateUtils.getNowDate());
        return stockPositionPlanMapper.updateStockPositionPlan(stockPositionPlan);
    }

    /**
     * 批量删除股票持仓计划
     * 
     * @param ids 需要删除的股票持仓计划主键
     * @return 结果
     */
    @Override
    public int deleteStockPositionPlanByIds(Long[] ids)
    {
        return stockPositionPlanMapper.deleteStockPositionPlanByIds(ids);
    }

    /**
     * 删除股票持仓计划信息
     * 
     * @param id 股票持仓计划主键
     * @return 结果
     */
    @Override
    public int deleteStockPositionPlanById(Long id)
    {
        return stockPositionPlanMapper.deleteStockPositionPlanById(id);
    }

    @Override
    public void deleteStockPositionPlanByTraceId(Long traceId) {
        stockPositionPlanMapper.deleteStockPositionPlanByTraceId(traceId);
    }
}
