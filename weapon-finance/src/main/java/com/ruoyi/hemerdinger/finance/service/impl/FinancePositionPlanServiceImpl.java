package com.ruoyi.hemerdinger.finance.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hemerdinger.finance.mapper.FinancePositionPlanMapper;
import com.ruoyi.hemerdinger.finance.domain.FinancePositionPlan;
import com.ruoyi.hemerdinger.finance.service.IFinancePositionPlanService;

/**
 * 持仓计划Service业务层处理
 * 
 * @author lijingxiang
 * @date 2023-11-26
 */
@Service
public class FinancePositionPlanServiceImpl implements IFinancePositionPlanService 
{
    @Autowired
    private FinancePositionPlanMapper financePositionPlanMapper;

    /**
     * 查询持仓计划
     * 
     * @param id 持仓计划主键
     * @return 持仓计划
     */
    @Override
    public FinancePositionPlan selectFinancePositionPlanById(Long id)
    {
        return financePositionPlanMapper.selectFinancePositionPlanById(id);
    }

    /**
     * 查询持仓计划列表
     * 
     * @param financePositionPlan 持仓计划
     * @return 持仓计划
     */
    @Override
    public List<FinancePositionPlan> selectFinancePositionPlanList(FinancePositionPlan financePositionPlan)
    {
        return financePositionPlanMapper.selectFinancePositionPlanList(financePositionPlan);
    }

    /**
     * 新增持仓计划
     * 
     * @param financePositionPlan 持仓计划
     * @return 结果
     */
    @Override
    public int insertFinancePositionPlan(FinancePositionPlan financePositionPlan)
    {
        financePositionPlan.setCreateTime(DateUtils.getNowDate());
        return financePositionPlanMapper.insertFinancePositionPlan(financePositionPlan);
    }

    /**
     * 修改持仓计划
     * 
     * @param financePositionPlan 持仓计划
     * @return 结果
     */
    @Override
    public int updateFinancePositionPlan(FinancePositionPlan financePositionPlan)
    {
        financePositionPlan.setUpdateTime(DateUtils.getNowDate());
        return financePositionPlanMapper.updateFinancePositionPlan(financePositionPlan);
    }

    /**
     * 批量删除持仓计划
     * 
     * @param ids 需要删除的持仓计划主键
     * @return 结果
     */
    @Override
    public int deleteFinancePositionPlanByIds(Long[] ids)
    {
        return financePositionPlanMapper.deleteFinancePositionPlanByIds(ids);
    }

    /**
     * 删除持仓计划信息
     * 
     * @param id 持仓计划主键
     * @return 结果
     */
    @Override
    public int deleteFinancePositionPlanById(Long id)
    {
        return financePositionPlanMapper.deleteFinancePositionPlanById(id);
    }
}
