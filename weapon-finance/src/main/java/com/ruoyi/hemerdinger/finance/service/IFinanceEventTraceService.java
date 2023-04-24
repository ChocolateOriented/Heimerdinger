package com.ruoyi.hemerdinger.finance.service;

import java.util.List;
import com.ruoyi.hemerdinger.finance.domain.FinanceEventTrace;

/**
 * 经济事件追踪Service接口
 * 
 * @author lijingxiang
 * @date 2022-05-11
 */
public interface IFinanceEventTraceService 
{
    /**
     * 查询经济事件追踪
     * 
     * @param id 经济事件追踪主键
     * @return 经济事件追踪
     */
    public FinanceEventTrace selectFinanceEventTraceById(Long id);

    /**
     * 查询经济事件追踪列表
     * 
     * @param financeEventTrace 经济事件追踪
     * @return 经济事件追踪集合
     */
    public List<FinanceEventTrace> selectFinanceEventTraceList(FinanceEventTrace financeEventTrace);

    /**
     * 新增经济事件追踪
     * 
     * @param financeEventTrace 经济事件追踪
     * @return 结果
     */
    public int insertFinanceEventTrace(FinanceEventTrace financeEventTrace);

    /**
     * 修改经济事件追踪
     * 
     * @param financeEventTrace 经济事件追踪
     * @return 结果
     */
    public int updateFinanceEventTrace(FinanceEventTrace financeEventTrace);

    /**
     * 批量删除经济事件追踪
     * 
     * @param ids 需要删除的经济事件追踪主键集合
     * @return 结果
     */
    public int deleteFinanceEventTraceByIds(Long[] ids);

    /**
     * 删除经济事件追踪信息
     * 
     * @param id 经济事件追踪主键
     * @return 结果
     */
    public int deleteFinanceEventTraceById(Long id);
}
