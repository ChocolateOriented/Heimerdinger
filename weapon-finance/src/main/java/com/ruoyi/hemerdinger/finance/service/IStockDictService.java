package com.ruoyi.hemerdinger.finance.service;

import java.util.List;
import com.ruoyi.hemerdinger.finance.domain.StockDict;

/**
 * 股票字典Service接口
 * 
 * @author lijingxiang
 * @date 2024-12-10
 */
public interface IStockDictService 
{
    /**
     * 查询股票字典
     * 
     * @param id 股票字典主键
     * @return 股票字典
     */
    public StockDict selectStockDictById(Long id);

    /**
     * 查询股票字典列表
     * 
     * @param stockDict 股票字典
     * @return 股票字典集合
     */
    public List<StockDict> selectStockDictList(StockDict stockDict);

    /**
     * 新增股票字典
     * 
     * @param stockDict 股票字典
     * @return 结果
     */
    public int insertStockDict(StockDict stockDict);

    /**
     * 修改股票字典
     * 
     * @param stockDict 股票字典
     * @return 结果
     */
    public int updateStockDict(StockDict stockDict);

    /**
     * 批量删除股票字典
     * 
     * @param ids 需要删除的股票字典主键集合
     * @return 结果
     */
    public int deleteStockDictByIds(Long[] ids);

    /**
     * 删除股票字典信息
     * 
     * @param id 股票字典主键
     * @return 结果
     */
    public int deleteStockDictById(Long id);

    public void saveStockList();
}
