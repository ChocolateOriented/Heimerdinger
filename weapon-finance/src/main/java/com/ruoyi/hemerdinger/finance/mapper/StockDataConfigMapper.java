package com.ruoyi.hemerdinger.finance.mapper;

import com.ruoyi.hemerdinger.finance.domain.StockDataConfig;
import java.util.List;

/**
 * 股票数据映射Mapper接口
 *
 * @author lijignxiang
 * @date 2022-04-24
 */
public interface StockDataConfigMapper
{
    /**
     * 查询股票数据映射
     *
     * @param id 股票数据映射主键
     * @return 股票数据映射
     */
    public StockDataConfig selectStockDataConfigById(Long id);

    /**
     * 查询股票数据映射列表
     *
     * @param stockDataConfig 股票数据映射
     * @return 股票数据映射集合
     */
    public List<StockDataConfig> selectStockDataConfigList(StockDataConfig stockDataConfig);

    /**
     * 新增股票数据映射
     *
     * @param stockDataConfig 股票数据映射
     * @return 结果
     */
    public int insertStockDataConfig(StockDataConfig stockDataConfig);

    /**
     * 修改股票数据映射
     *
     * @param stockDataConfig 股票数据映射
     * @return 结果
     */
    public int updateStockDataConfig(StockDataConfig stockDataConfig);

    /**
     * 删除股票数据映射
     *
     * @param id 股票数据映射主键
     * @return 结果
     */
    public int deleteStockDataConfigById(Long id);

    /**
     * 批量删除股票数据映射
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockDataConfigByIds(Long[] ids);
}
