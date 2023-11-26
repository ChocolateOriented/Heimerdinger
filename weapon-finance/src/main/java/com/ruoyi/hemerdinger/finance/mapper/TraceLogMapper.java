package com.ruoyi.hemerdinger.finance.mapper;

import java.util.List;
import com.ruoyi.hemerdinger.finance.domain.TraceLog;

/**
 * 追踪日志Mapper接口
 * 
 * @author lijingxiang
 * @date 2023-11-26
 */
public interface TraceLogMapper 
{
    /**
     * 查询追踪日志
     * 
     * @param id 追踪日志主键
     * @return 追踪日志
     */
    public TraceLog selectTraceLogById(Long id);

    /**
     * 查询追踪日志列表
     * 
     * @param traceLog 追踪日志
     * @return 追踪日志集合
     */
    public List<TraceLog> selectTraceLogList(TraceLog traceLog);

    /**
     * 新增追踪日志
     * 
     * @param traceLog 追踪日志
     * @return 结果
     */
    public int insertTraceLog(TraceLog traceLog);

    /**
     * 修改追踪日志
     * 
     * @param traceLog 追踪日志
     * @return 结果
     */
    public int updateTraceLog(TraceLog traceLog);

    /**
     * 删除追踪日志
     * 
     * @param id 追踪日志主键
     * @return 结果
     */
    public int deleteTraceLogById(Long id);

    /**
     * 批量删除追踪日志
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTraceLogByIds(Long[] ids);
}
