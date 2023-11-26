package com.ruoyi.hemerdinger.finance.mapper;

import java.util.List;
import com.ruoyi.hemerdinger.finance.domain.FinancePositionPlan;

/**
 * 持仓计划Mapper接口
 * 
 * @author lijingxiang
 * @date 2023-11-26
 */
public interface FinancePositionPlanMapper 
{
    /**
     * 查询持仓计划
     * 
     * @param id 持仓计划主键
     * @return 持仓计划
     */
    public FinancePositionPlan selectFinancePositionPlanById(Long id);

    /**
     * 查询持仓计划列表
     * 
     * @param financePositionPlan 持仓计划
     * @return 持仓计划集合
     */
    public List<FinancePositionPlan> selectFinancePositionPlanList(FinancePositionPlan financePositionPlan);

    /**
     * 新增持仓计划
     * 
     * @param financePositionPlan 持仓计划
     * @return 结果
     */
    public int insertFinancePositionPlan(FinancePositionPlan financePositionPlan);

    /**
     * 修改持仓计划
     * 
     * @param financePositionPlan 持仓计划
     * @return 结果
     */
    public int updateFinancePositionPlan(FinancePositionPlan financePositionPlan);

    /**
     * 删除持仓计划
     * 
     * @param id 持仓计划主键
     * @return 结果
     */
    public int deleteFinancePositionPlanById(Long id);

    /**
     * 批量删除持仓计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinancePositionPlanByIds(Long[] ids);
}
