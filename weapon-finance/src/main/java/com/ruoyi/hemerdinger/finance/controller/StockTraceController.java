package com.ruoyi.hemerdinger.finance.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.hemerdinger.finance.service.IStockTraceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Rest;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hemerdinger.finance.domain.StockTrace;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 股票追踪Controller
 *
 * @author lijingxiang
 * @date 2022-04-24
 */
@Api("股票追踪管理")
@RestController
@RequestMapping("/finance/stockTrace")
public class StockTraceController extends BaseController
{
    @Autowired
    private IStockTraceService stockTraceService;

    @ApiOperation("查询股票追踪列表")
    @ApiOperationSupport(author = "lijingxiang")
    @GetMapping("/list")
    public TableDataInfo<List<StockTrace>> list(StockTrace stockTrace)
    {
        startPage();
        List<StockTrace> list = stockTraceService.selectStockTraceList(stockTrace);
        return getDataTable(list);
    }

    @ApiOperation("导出股票追踪列表")
    @ApiOperationSupport(author = "lijingxiang")
    @Log(title = "股票追踪", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Rest export(StockTrace stockTrace)
    {
        List<StockTrace> list = stockTraceService.selectStockTraceList(stockTrace);
        ExcelUtil<StockTrace> util = new ExcelUtil<StockTrace>(StockTrace.class);
        return util.exportExcel(list, "股票追踪数据");
    }

    @ApiOperation("获取股票追踪详细信息")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "${pkColumn.columnComment}", required = true)
    @GetMapping(value = "/{id}")
    public Rest<StockTrace> getInfo(@PathVariable("id") Long id)
    {
        return Rest.success(stockTraceService.selectStockTraceById(id));
    }

    @ApiOperation("获取股票当前信息")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "code", value = "${pkColumn.columnComment}", required = true)
    @GetMapping(value = "/findCurrentInfo")
    public Rest<JSONObject> findCurrentInfo(@RequestParam("code") String code)
    {
        return Rest.success(stockTraceService.findCurrentInfo(code));
    }


    @ApiOperation("新增股票追踪")
    @ApiOperationSupport(author = "lijingxiang")
    @Log(title = "股票追踪", businessType = BusinessType.INSERT)
    @PostMapping
    public Rest<StockTrace> add(@RequestBody StockTrace stockTrace)
    {
        return toAjax(stockTraceService.insertStockTrace(stockTrace));
    }

    @ApiOperation("修改股票追踪")
    @ApiOperationSupport(author = "lijingxiang")
    @Log(title = "股票追踪", businessType = BusinessType.UPDATE)
    @PutMapping
    public Rest<StockTrace> edit(@RequestBody StockTrace stockTrace)
    {
        return toAjax(stockTraceService.updateStockTrace(stockTrace));
    }

    @ApiOperation("删除股票追踪")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "${pkColumn.columnComment}", required = true)
    @Log(title = "股票追踪", businessType = BusinessType.DELETE)
	  @DeleteMapping("/{ids}")
    public Rest remove(@PathVariable Long[] ids)
    {
        return toAjax(stockTraceService.deleteStockTraceByIds(ids));
    }
}
