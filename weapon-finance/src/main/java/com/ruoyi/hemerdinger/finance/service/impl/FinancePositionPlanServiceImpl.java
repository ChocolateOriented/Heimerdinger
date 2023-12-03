package com.ruoyi.hemerdinger.finance.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.hemerdinger.finance.domain.StockTrace;
import com.ruoyi.hemerdinger.finance.service.IStockTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hemerdinger.finance.mapper.FinancePositionPlanMapper;
import com.ruoyi.hemerdinger.finance.domain.FinancePositionPlan;
import com.ruoyi.hemerdinger.finance.service.IFinancePositionPlanService;
import org.springframework.util.CollectionUtils;

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
    @Autowired
    private IStockTraceService stockTraceService;
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
        List<FinancePositionPlan> list = financePositionPlanMapper.selectFinancePositionPlanList(financePositionPlan);
        //计算个板块实际持仓
        for (int i = 0; i < list.size(); i++) {
            FinancePositionPlan positionPlan = list.get(i);
            List<StockTrace> stockTraceList = positionPlan.getStockTraceList();
            BigDecimal realityAmount = positionPlan.getRealityAmount();
            if (null == realityAmount){
                realityAmount = new BigDecimal(0);
            }
            if (CollectionUtils.isEmpty(stockTraceList)){
                continue;
            }
            for (int j = 0; j < stockTraceList.size(); j++) {
                StockTrace stockTrace = stockTraceList.get(j);
                String code = stockTrace.getCode();
                //兼容现金
                if(StringUtils.isBlank(code)){
                    realityAmount = realityAmount.add(stockTrace.getQuotient());
                    continue;
                }
                JSONObject currentInfo = stockTraceService.findCurrentInfo(stockTrace.getCode());
                BigDecimal price = new BigDecimal(currentInfo.getDouble("price")) ;
                BigDecimal amount = price.multiply(stockTrace.getQuotient());
                realityAmount = realityAmount.add(amount);
            }
            positionPlan.setRealityAmount(realityAmount);
        }
        return list;
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
