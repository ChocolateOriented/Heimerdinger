package com.ruoyi.hemerdinger.finance.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.hemerdinger.finance.manager.AkShareManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import com.ruoyi.hemerdinger.finance.mapper.StockDictMapper;
import com.ruoyi.hemerdinger.finance.domain.StockDict;
import com.ruoyi.hemerdinger.finance.service.IStockDictService;
import org.springframework.util.CollectionUtils;

/**
 * 股票字典Service业务层处理
 *
 * @author lijingxiang
 * @date 2024-12-10
 */
@Service("stockDictService")
public class StockDictServiceImpl implements IStockDictService {
    @Autowired
    private StockDictMapper stockDictMapper;
    @Autowired
    private AkShareManager akShareManager;
    /**
     * 查询股票字典
     *
     * @param id 股票字典主键
     * @return 股票字典
     */
    @Override
    public StockDict selectStockDictById(Long id) {
        return stockDictMapper.selectStockDictById(id);
    }

    /**
     * 查询股票字典列表
     *
     * @param stockDict 股票字典
     * @return 股票字典
     */
    @Override
    public List<StockDict> selectStockDictList(StockDict stockDict) {
        return stockDictMapper.selectStockDictList(stockDict);
    }

    /**
     * 新增股票字典
     *
     * @param stockDict 股票字典
     * @return 结果
     */
    @Override
    public int insertStockDict(StockDict stockDict) {
        return stockDictMapper.insertStockDict(stockDict);
    }

    /**
     * 修改股票字典
     *
     * @param stockDict 股票字典
     * @return 结果
     */
    @Override
    public int updateStockDict(StockDict stockDict) {
        return stockDictMapper.updateStockDict(stockDict);
    }

    /**
     * 批量删除股票字典
     *
     * @param ids 需要删除的股票字典主键
     * @return 结果
     */
    @Override
    public int deleteStockDictByIds(Long[] ids) {
        return stockDictMapper.deleteStockDictByIds(ids);
    }

    /**
     * 删除股票字典信息
     *
     * @param id 股票字典主键
     * @return 结果
     */
    @Override
    public int deleteStockDictById(Long id) {
        return stockDictMapper.deleteStockDictById(id);
    }

    /**
     * 调用AKShare接口stock_sh_a_spot_em保存股票列表
     */
    @Override
    public void saveStockList() {
        // 沪A
        String path = "stock_sh_a_spot_em";
        doSave(path, "sh");
        // 科创
        path = "stock_kc_a_spot_em";
        doSave(path, "sh");
        // 深A
        path = "stock_sz_a_spot_em";
        doSave(path, "sz");
        // 创业板
        path = "stock_cy_a_spot_em";
        doSave(path, "sz");
        // 北交
        path = "stock_bj_a_spot_em";
        doSave(path, "bj");
    }

    private void doSave(String path, String bourse) {
        String result = akShareManager.find(path, null);
        JSONArray stockJsonArray = JSON.parseArray(result);
        if (CollectionUtils.isEmpty(stockJsonArray)) {
            return;
        }
        for (int i = 0; i < stockJsonArray.size(); i++) {
            JSONObject stockJson = stockJsonArray.getJSONObject(i);
            StockDict stock = new StockDict();
            stock.setCode(stockJson.getString("代码"));
            stock.setName(stockJson.getString("名称"));
            stock.setBourse(bourse);
            this.saveOrUpdate(stock);
        }
    }

    private void saveOrUpdate(StockDict stock) {
        StockDict query = new StockDict();
        query.setCode(stock.getCode());
        List<StockDict> stockDicts = stockDictMapper.selectStockDictList(query);
        if (CollectionUtils.isEmpty(stockDicts)){
            stockDictMapper.insertStockDict(stock);
            return;
        }
        StockDict old = stockDicts.get(0);
        if (StringUtils.endsWith(old.getName(),stock.getName())){
            return;
        }
        stock.setId(stockDicts.get(0).getId());
        stockDictMapper.updateStockDict(stock);
    }

}
