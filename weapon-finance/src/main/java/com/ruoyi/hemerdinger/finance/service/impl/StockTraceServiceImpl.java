package com.ruoyi.hemerdinger.finance.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.framework.web.service.UserDetailsServiceImpl;
import com.ruoyi.hemerdinger.finance.domain.StockDataConfig;
import com.ruoyi.hemerdinger.finance.manager.StockManager;
import com.ruoyi.hemerdinger.finance.mapper.StockDataConfigMapper;
import com.ruoyi.hemerdinger.finance.mapper.StockTraceMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.ruoyi.hemerdinger.finance.domain.StockTrace;
import com.ruoyi.hemerdinger.finance.service.IStockTraceService;

/**
 * 股票追踪Service业务层处理
 * 
 * @author lijingxiang
 * @date 2023-11-26
 */
@Service
public class StockTraceServiceImpl implements IStockTraceService 
{
    private static final Logger log = LoggerFactory.getLogger(StockTraceServiceImpl.class);

    @Autowired
    private StockTraceMapper stockTraceMapper;
    @Autowired
    private StockManager stockManager;
    @Autowired
    private StockDataConfigMapper dataConfigMapper;

    /**
     * 查询股票追踪
     * 
     * @param id 股票追踪主键
     * @return 股票追踪
     */
    @Override
    public StockTrace selectStockTraceById(Long id)
    {
        return stockTraceMapper.selectStockTraceById(id);
    }

    /**
     * 查询股票追踪列表
     * 
     * @param stockTrace 股票追踪
     * @return 股票追踪
     */
    @Override
    public List<StockTrace> selectStockTraceList(StockTrace stockTrace)
    {
        return stockTraceMapper.selectStockTraceList(stockTrace);
    }

    /**
     * 新增股票追踪
     * 
     * @param stockTrace 股票追踪
     * @return 结果
     */
    @Override
    public int insertStockTrace(StockTrace stockTrace)
    {
        return stockTraceMapper.insertStockTrace(stockTrace);
    }

    /**
     * 修改股票追踪
     * 
     * @param stockTrace 股票追踪
     * @return 结果
     */
    @Override
    public int updateStockTrace(StockTrace stockTrace)
    {
        return stockTraceMapper.updateStockTrace(stockTrace);
    }

    /**
     * 批量删除股票追踪
     * 
     * @param ids 需要删除的股票追踪主键
     * @return 结果
     */
    @Override
    public int deleteStockTraceByIds(Long[] ids)
    {
        return stockTraceMapper.deleteStockTraceByIds(ids);
    }

    /**
     * 删除股票追踪信息
     * 
     * @param id 股票追踪主键
     * @return 结果
     */
    @Override
    public int deleteStockTraceById(Long id)
    {
        return stockTraceMapper.deleteStockTraceById(id);
    }

    @Override
//    @Cacheable(value = "stockCurrentInfo",key = "#code")
    public JSONObject findCurrentInfo(String code) {
        String stockString = stockManager.findStock(code);
        String[] split = stockString.split("\\,");
        for (int i = 0; i < split.length; i++) {
            log.info(i+":"+split[i]);
        }
        JSONObject stockInfo = new JSONObject();
        try {
            List<StockDataConfig> stockDataConfigs = dataConfigMapper.selectStockDataConfigList(new StockDataConfig());
            for (int i = 0; i < stockDataConfigs.size(); i++) {
                StockDataConfig stockDataConfig = stockDataConfigs.get(i);
                Long index = stockDataConfig.getDataIndex();
                String name = stockDataConfig.getName();
                stockInfo.put(name, split[index.intValue()]);
            }
        }catch (Exception e){
            log.error("获取股票信息失败:"+code,e);
        }
        return stockInfo;
    }
}
