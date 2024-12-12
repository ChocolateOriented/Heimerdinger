package com.ruoyi.hemerdinger.finance.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.hemerdinger.finance.domain.StockTrace;
import com.ruoyi.hemerdinger.finance.domain.vo.LineReq;

import java.util.List;
import java.util.Map;

/**
 * 指标Service接口
 *
 * @author lijingxiang
 * @date 2022-04-24
 */
public interface IIndicatorService {
    void updateDaysIndicators();
    void updateMonthsIndicators();

    List<JSONObject> findFromMongo(LineReq req);
}
