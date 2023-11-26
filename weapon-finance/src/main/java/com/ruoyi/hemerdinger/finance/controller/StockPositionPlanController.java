package com.ruoyi.hemerdinger.finance.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Rest;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hemerdinger.finance.domain.StockPositionPlan;
import com.ruoyi.hemerdinger.finance.service.IStockPositionPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 股票持仓计划Controller
 *
 * @author lijingxiang
 * @date 2023-11-26
 */
@Api("股票持仓计划管理")
@RestController
@RequestMapping("/finance/stockPositionPlan")
public class StockPositionPlanController extends BaseController
{
    @Autowired
    private IStockPositionPlanService stockPositionPlanService;

    @ApiOperation("查询股票持仓计划列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:stockPositionPlan:list')")
    @GetMapping("/list")
    public TableDataInfo<List<StockPositionPlan>> list(StockPositionPlan stockPositionPlan)
    {
        startPage();
        List<StockPositionPlan> list = stockPositionPlanService.selectStockPositionPlanList(stockPositionPlan);
        return getDataTable(list);
    }

    @ApiOperation("导出股票持仓计划列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:stockPositionPlan:export')")
    @Log(title = "股票持仓计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Rest export(StockPositionPlan stockPositionPlan)
    {
        List<StockPositionPlan> list = stockPositionPlanService.selectStockPositionPlanList(stockPositionPlan);
        ExcelUtil<StockPositionPlan> util = new ExcelUtil<StockPositionPlan>(StockPositionPlan.class);
        return util.exportExcel(list, "股票持仓计划数据");
    }

    @ApiOperation("获取股票持仓计划详细信息")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('finance:stockPositionPlan:query')")
    @GetMapping(value = "/{id}")
    public Rest<StockPositionPlan> getInfo(@PathVariable("id") Long id)
    {
        return Rest.success(stockPositionPlanService.selectStockPositionPlanById(id));
    }


    @ApiOperation("新增股票持仓计划")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:stockPositionPlan:add')")
    @Log(title = "股票持仓计划", businessType = BusinessType.INSERT)
    @PostMapping
    public Rest<StockPositionPlan> add(@RequestBody StockPositionPlan stockPositionPlan)
    {
        return toAjax(stockPositionPlanService.insertStockPositionPlan(stockPositionPlan));
    }

    @ApiOperation("新增股票持仓计划")
    @ApiOperationSupport(author = "lijing xiang")
    @PreAuthorize("@ss.hasPermi('finance:stockPositionPlan:add')")
    @Log(title = "股票持仓计划", businessType = BusinessType.INSERT)
    @PostMapping("saveStockPositionPlanList")
    public Rest<StockPositionPlan> saveStockPositionPlanList(@RequestBody List<StockPositionPlan> stockPositionPlanList)
    {
        if (CollectionUtils.isEmpty(stockPositionPlanList)){
            return Rest.success();
        }
        Long traceId = stockPositionPlanList.get(0).getTraceId();
        stockPositionPlanService.deleteStockPositionPlanByTraceId(traceId);
        for (int i = 0; i < stockPositionPlanList.size(); i++) {
            stockPositionPlanService.insertStockPositionPlan(stockPositionPlanList.get(i));
        }
        return Rest.success();
    }

    @ApiOperation("修改股票持仓计划")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:stockPositionPlan:edit')")
    @Log(title = "股票持仓计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public Rest<StockPositionPlan> edit(@RequestBody StockPositionPlan stockPositionPlan)
    {
        return toAjax(stockPositionPlanService.updateStockPositionPlan(stockPositionPlan));
    }

    @ApiOperation("删除股票持仓计划")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('finance:stockPositionPlan:remove')")
    @Log(title = "股票持仓计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Rest remove(@PathVariable Long[] ids)
    {
        return toAjax(stockPositionPlanService.deleteStockPositionPlanByIds(ids));
    }
}
