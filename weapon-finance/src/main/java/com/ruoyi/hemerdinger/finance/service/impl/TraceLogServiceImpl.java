package com.ruoyi.hemerdinger.finance.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hemerdinger.finance.mapper.TraceLogMapper;
import com.ruoyi.hemerdinger.finance.domain.TraceLog;
import com.ruoyi.hemerdinger.finance.service.ITraceLogService;

/**
 * 追踪日志Service业务层处理
 * 
 * @author lijingxiang
 * @date 2023-11-26
 */
@Service
public class TraceLogServiceImpl implements ITraceLogService 
{
    @Autowired
    private TraceLogMapper traceLogMapper;

    /**
     * 查询追踪日志
     * 
     * @param id 追踪日志主键
     * @return 追踪日志
     */
    @Override
    public TraceLog selectTraceLogById(Long id)
    {
        return traceLogMapper.selectTraceLogById(id);
    }

    /**
     * 查询追踪日志列表
     * 
     * @param traceLog 追踪日志
     * @return 追踪日志
     */
    @Override
    public List<TraceLog> selectTraceLogList(TraceLog traceLog)
    {
        return traceLogMapper.selectTraceLogList(traceLog);
    }

    /**
     * 新增追踪日志
     * 
     * @param traceLog 追踪日志
     * @return 结果
     */
    @Override
    public int insertTraceLog(TraceLog traceLog)
    {
        traceLog.setCreateTime(DateUtils.getNowDate());
        return traceLogMapper.insertTraceLog(traceLog);
    }

    /**
     * 修改追踪日志
     * 
     * @param traceLog 追踪日志
     * @return 结果
     */
    @Override
    public int updateTraceLog(TraceLog traceLog)
    {
        traceLog.setUpdateTime(DateUtils.getNowDate());
        return traceLogMapper.updateTraceLog(traceLog);
    }

    /**
     * 批量删除追踪日志
     * 
     * @param ids 需要删除的追踪日志主键
     * @return 结果
     */
    @Override
    public int deleteTraceLogByIds(Long[] ids)
    {
        return traceLogMapper.deleteTraceLogByIds(ids);
    }

    /**
     * 删除追踪日志信息
     * 
     * @param id 追踪日志主键
     * @return 结果
     */
    @Override
    public int deleteTraceLogById(Long id)
    {
        return traceLogMapper.deleteTraceLogById(id);
    }
}
