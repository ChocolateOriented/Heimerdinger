package com.ruoyi.hemerdinger.finance.mapper;

import java.util.List;
import com.ruoyi.hemerdinger.finance.domain.StockDataConfig;

/**
 * 股票数据映射配置Mapper接口
 * 
 * @author lijing xiang
 * @date 2022-04-27
 */
public interface StockDataConfigMapper 
{
    /**
     * 查询股票数据映射配置
     * 
     * @param id 股票数据映射配置主键
     * @return 股票数据映射配置
     */
    public StockDataConfig selectStockDataConfigById(Long id);

    /**
     * 查询股票数据映射配置列表
     * 
     * @param stockDataConfig 股票数据映射配置
     * @return 股票数据映射配置集合
     */
    public List<StockDataConfig> selectStockDataConfigList(StockDataConfig stockDataConfig);

    /**
     * 新增股票数据映射配置
     * 
     * @param stockDataConfig 股票数据映射配置
     * @return 结果
     */
    public int insertStockDataConfig(StockDataConfig stockDataConfig);

    /**
     * 修改股票数据映射配置
     * 
     * @param stockDataConfig 股票数据映射配置
     * @return 结果
     */
    public int updateStockDataConfig(StockDataConfig stockDataConfig);

    /**
     * 删除股票数据映射配置
     * 
     * @param id 股票数据映射配置主键
     * @return 结果
     */
    public int deleteStockDataConfigById(Long id);

    /**
     * 批量删除股票数据映射配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockDataConfigByIds(Long[] ids);
}
