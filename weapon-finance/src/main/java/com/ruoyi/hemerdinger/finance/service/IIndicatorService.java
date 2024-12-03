package com.ruoyi.hemerdinger.finance.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.hemerdinger.finance.domain.StockTrace;

import java.util.List;
import java.util.Map;

/**
 * 指标Service接口
 *
 * @author lijingxiang
 * @date 2022-04-24
 */
public interface IIndicatorService
{
    /**
     * 更新指标
     * @return 股票追踪
     */
    public void updateIndicators();

    Iterable findAKShare(String path, Map<String, String> param);

    /**
     * 保存或更新指标
     * @param dataList
     */
    void saveOrUpdateIndicator(List<JSONObject> dataList);
}
