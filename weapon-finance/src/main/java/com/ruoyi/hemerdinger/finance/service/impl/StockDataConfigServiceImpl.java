package com.ruoyi.hemerdinger.finance.service.impl;

import com.ruoyi.hemerdinger.finance.domain.StockDataConfig;
import com.ruoyi.hemerdinger.finance.mapper.StockDataConfigMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hemerdinger.finance.service.IStockDataConfigService;

/**
 * 股票数据映射Service业务层处理
 *
 * @author lijignxiang
 * @date 2022-04-24
 */
@Service
public class StockDataConfigServiceImpl implements IStockDataConfigService
{
    @Autowired
    private StockDataConfigMapper stockDataConfigMapper;

    /**
     * 查询股票数据映射
     *
     * @param id 股票数据映射主键
     * @return 股票数据映射
     */
    @Override
    public StockDataConfig selectStockDataConfigById(Long id)
    {
        return stockDataConfigMapper.selectStockDataConfigById(id);
    }

    /**
     * 查询股票数据映射列表
     *
     * @param stockDataConfig 股票数据映射
     * @return 股票数据映射
     */
    @Override
    public List<StockDataConfig> selectStockDataConfigList(StockDataConfig stockDataConfig)
    {
        return stockDataConfigMapper.selectStockDataConfigList(stockDataConfig);
    }

    /**
     * 新增股票数据映射
     *
     * @param stockDataConfig 股票数据映射
     * @return 结果
     */
    @Override
    public int insertStockDataConfig(StockDataConfig stockDataConfig)
    {
        return stockDataConfigMapper.insertStockDataConfig(stockDataConfig);
    }

    /**
     * 修改股票数据映射
     *
     * @param stockDataConfig 股票数据映射
     * @return 结果
     */
    @Override
    public int updateStockDataConfig(StockDataConfig stockDataConfig)
    {
        return stockDataConfigMapper.updateStockDataConfig(stockDataConfig);
    }

    /**
     * 批量删除股票数据映射
     *
     * @param ids 需要删除的股票数据映射主键
     * @return 结果
     */
    @Override
    public int deleteStockDataConfigByIds(Long[] ids)
    {
        return stockDataConfigMapper.deleteStockDataConfigByIds(ids);
    }

    /**
     * 删除股票数据映射信息
     *
     * @param id 股票数据映射主键
     * @return 结果
     */
    @Override
    public int deleteStockDataConfigById(Long id)
    {
        return stockDataConfigMapper.deleteStockDataConfigById(id);
    }
}
