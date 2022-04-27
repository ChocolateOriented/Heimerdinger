package com.ruoyi.hemerdinger.finance.controller;

import com.ruoyi.hemerdinger.finance.domain.StockDataConfig;
import com.ruoyi.hemerdinger.finance.service.IStockDataConfigService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Rest;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 股票数据映射Controller
 *
 * @author lijignxiang
 * @date 2022-04-24
 */
@Api("股票数据映射管理")
@RestController
@RequestMapping("/finance/stockDataConfig")
public class StockDataConfigController extends BaseController
{
    @Autowired
    private IStockDataConfigService stockDataConfigService;

    @ApiOperation("查询股票数据映射列表")
    @ApiOperationSupport(author = "lijignxiang")
    @GetMapping("/list")
    public TableDataInfo<List<StockDataConfig>> list(StockDataConfig stockDataConfig)
    {
        startPage();
        List<StockDataConfig> list = stockDataConfigService.selectStockDataConfigList(stockDataConfig);
        return getDataTable(list);
    }

    @ApiOperation("导出股票数据映射列表")
    @ApiOperationSupport(author = "lijignxiang")
    @Log(title = "股票数据映射", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Rest export(StockDataConfig stockDataConfig)
    {
        List<StockDataConfig> list = stockDataConfigService.selectStockDataConfigList(stockDataConfig);
        ExcelUtil<StockDataConfig> util = new ExcelUtil<StockDataConfig>(StockDataConfig.class);
        return util.exportExcel(list, "股票数据映射数据");
    }

    @ApiOperation("获取股票数据映射详细信息")
    @ApiOperationSupport(author = "lijignxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping(value = "/{id}")
    public Rest<StockDataConfig> getInfo(@PathVariable("id") Long id)
    {
        return Rest.success(stockDataConfigService.selectStockDataConfigById(id));
    }


    @ApiOperation("新增股票数据映射")
    @ApiOperationSupport(author = "lijignxiang")
    @Log(title = "股票数据映射", businessType = BusinessType.INSERT)
    @PostMapping
    public Rest<StockDataConfig> add(@RequestBody StockDataConfig stockDataConfig)
    {
        return toAjax(stockDataConfigService.insertStockDataConfig(stockDataConfig));
    }

    @ApiOperation("修改股票数据映射")
    @ApiOperationSupport(author = "lijignxiang")
    @Log(title = "股票数据映射", businessType = BusinessType.UPDATE)
    @PutMapping
    public Rest<StockDataConfig> edit(@RequestBody StockDataConfig stockDataConfig)
    {
        return toAjax(stockDataConfigService.updateStockDataConfig(stockDataConfig));
    }

    @ApiOperation("删除股票数据映射")
    @ApiOperationSupport(author = "lijignxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @Log(title = "股票数据映射", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Rest remove(@PathVariable Long[] ids)
    {
        return toAjax(stockDataConfigService.deleteStockDataConfigByIds(ids));
    }
}
