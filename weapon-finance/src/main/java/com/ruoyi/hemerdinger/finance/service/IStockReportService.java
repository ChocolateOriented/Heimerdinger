package com.ruoyi.hemerdinger.finance.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.hemerdinger.finance.domain.StockReport;
import com.ruoyi.hemerdinger.finance.domain.vo.StockSubjectListVo;

import java.util.List;

/**
 * 股票经营报告Service接口
 * 
 * @author lijingxiang
 * @date 2024-12-10
 */
public interface IStockReportService 
{
    List<StockSubjectListVo> list(String keyword);

    List<StockReport> reportList(String code);

//    /**
//     * 查询股票经营报告
//     *
//     * @param id 股票经营报告主键
//     * @return 股票经营报告
//     */
//    public StockReport selectStockReportById(Long id);
//
//    /**
//     * 查询股票经营报告列表
//     *
//     * @param stockReport 股票经营报告
//     * @return 股票经营报告集合
//     */
//    public List<StockReport> selectStockReportList(StockReport stockReport);
//
//    /**
//     * 新增股票经营报告
//     *
//     * @param stockReport 股票经营报告
//     * @return 结果
//     */
//    public int insertStockReport(StockReport stockReport);
//
//    /**
//     * 修改股票经营报告
//     *
//     * @param stockReport 股票经营报告
//     * @return 结果
//     */
//    public int updateStockReport(StockReport stockReport);
//
//    /**
//     * 批量删除股票经营报告
//     *
//     * @param ids 需要删除的股票经营报告主键集合
//     * @return 结果
//     */
//    public int deleteStockReportByIds(Long[] ids);
//
//    /**
//     * 删除股票经营报告信息
//     *
//     * @param id 股票经营报告主键
//     * @return 结果
//     */
//    public int deleteStockReportById(Long id);
//
//    public void saveStockList();
}
