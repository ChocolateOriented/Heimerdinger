package com.ruoyi.hemerdinger.finance.mapper;

import java.util.List;
import com.ruoyi.hemerdinger.finance.domain.StockTrace;

/**
 * 股票追踪Mapper接口
 * 
 * @author lijingxiang
 * @date 2023-11-26
 */
public interface StockTraceMapper 
{
    /**
     * 查询股票追踪
     * 
     * @param id 股票追踪主键
     * @return 股票追踪
     */
    public StockTrace selectStockTraceById(Long id);

    /**
     * 查询股票追踪列表
     * 
     * @param stockTrace 股票追踪
     * @return 股票追踪集合
     */
    public List<StockTrace> selectStockTraceList(StockTrace stockTrace);

    /**
     * 新增股票追踪
     * 
     * @param stockTrace 股票追踪
     * @return 结果
     */
    public int insertStockTrace(StockTrace stockTrace);

    /**
     * 修改股票追踪
     * 
     * @param stockTrace 股票追踪
     * @return 结果
     */
    public int updateStockTrace(StockTrace stockTrace);

    /**
     * 删除股票追踪
     * 
     * @param id 股票追踪主键
     * @return 结果
     */
    public int deleteStockTraceById(Long id);

    /**
     * 批量删除股票追踪
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockTraceByIds(Long[] ids);
}
