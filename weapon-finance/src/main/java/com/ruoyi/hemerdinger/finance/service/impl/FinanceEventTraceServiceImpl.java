package com.ruoyi.hemerdinger.finance.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hemerdinger.finance.mapper.FinanceEventTraceMapper;
import com.ruoyi.hemerdinger.finance.domain.FinanceEventTrace;
import com.ruoyi.hemerdinger.finance.service.IFinanceEventTraceService;

/**
 * 经济事件追踪Service业务层处理
 * 
 * @author lijingxiang
 * @date 2022-05-11
 */
@Service
public class FinanceEventTraceServiceImpl implements IFinanceEventTraceService 
{
    @Autowired
    private FinanceEventTraceMapper financeEventTraceMapper;

    /**
     * 查询经济事件追踪
     * 
     * @param id 经济事件追踪主键
     * @return 经济事件追踪
     */
    @Override
    public FinanceEventTrace selectFinanceEventTraceById(Long id)
    {
        return financeEventTraceMapper.selectFinanceEventTraceById(id);
    }

    /**
     * 查询经济事件追踪列表
     * 
     * @param financeEventTrace 经济事件追踪
     * @return 经济事件追踪
     */
    @Override
    public List<FinanceEventTrace> selectFinanceEventTraceList(FinanceEventTrace financeEventTrace)
    {
        return financeEventTraceMapper.selectFinanceEventTraceList(financeEventTrace);
    }

    /**
     * 新增经济事件追踪
     * 
     * @param financeEventTrace 经济事件追踪
     * @return 结果
     */
    @Override
    public int insertFinanceEventTrace(FinanceEventTrace financeEventTrace)
    {
        financeEventTrace.setCreateTime(DateUtils.getNowDate());
        return financeEventTraceMapper.insertFinanceEventTrace(financeEventTrace);
    }

    /**
     * 修改经济事件追踪
     * 
     * @param financeEventTrace 经济事件追踪
     * @return 结果
     */
    @Override
    public int updateFinanceEventTrace(FinanceEventTrace financeEventTrace)
    {
        financeEventTrace.setUpdateTime(DateUtils.getNowDate());
        return financeEventTraceMapper.updateFinanceEventTrace(financeEventTrace);
    }

    /**
     * 批量删除经济事件追踪
     * 
     * @param ids 需要删除的经济事件追踪主键
     * @return 结果
     */
    @Override
    public int deleteFinanceEventTraceByIds(Long[] ids)
    {
        return financeEventTraceMapper.deleteFinanceEventTraceByIds(ids);
    }

    /**
     * 删除经济事件追踪信息
     * 
     * @param id 经济事件追踪主键
     * @return 结果
     */
    @Override
    public int deleteFinanceEventTraceById(Long id)
    {
        return financeEventTraceMapper.deleteFinanceEventTraceById(id);
    }
}
