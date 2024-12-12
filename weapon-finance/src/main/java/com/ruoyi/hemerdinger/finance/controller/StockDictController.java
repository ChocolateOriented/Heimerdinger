package com.ruoyi.hemerdinger.finance.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Rest;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hemerdinger.finance.domain.StockDict;
import com.ruoyi.hemerdinger.finance.service.IStockDictService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 股票字典Controller
 *
 * @author lijingxiang
 * @date 2024-12-10
 */
@Api("股票字典管理")
@RestController
@RequestMapping("/finance/stockDict")
public class StockDictController extends BaseController
{
    @Autowired
    private IStockDictService stockDictService;

    @ApiOperation("查询股票字典列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:stockDict:list')")
    @GetMapping("/list")
    public TableDataInfo<List<StockDict>> list(StockDict stockDict)
    {
        startPage();
        List<StockDict> list = stockDictService.selectStockDictList(stockDict);
        return getDataTable(list);
    }

    @ApiOperation("导出股票字典列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:stockDict:export')")
    @Log(title = "股票字典", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Rest export(StockDict stockDict)
    {
        List<StockDict> list = stockDictService.selectStockDictList(stockDict);
        ExcelUtil<StockDict> util = new ExcelUtil<StockDict>(StockDict.class);
        return util.exportExcel(list, "股票字典数据");
    }

    @ApiOperation("获取股票字典详细信息")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('finance:stockDict:query')")
    @GetMapping(value = "/{id}")
    public Rest<StockDict> getInfo(@PathVariable("id") Long id)
    {
        return Rest.success(stockDictService.selectStockDictById(id));
    }


    @ApiOperation("新增股票字典")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:stockDict:add')")
    @Log(title = "股票字典", businessType = BusinessType.INSERT)
    @PostMapping
    public Rest<StockDict> add(@RequestBody StockDict stockDict)
    {
        return toAjax(stockDictService.insertStockDict(stockDict));
    }

    @ApiOperation("修改股票字典")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:stockDict:edit')")
    @Log(title = "股票字典", businessType = BusinessType.UPDATE)
    @PutMapping
    public Rest<StockDict> edit(@RequestBody StockDict stockDict)
    {
        return toAjax(stockDictService.updateStockDict(stockDict));
    }

    @ApiOperation("删除股票字典")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('finance:stockDict:remove')")
    @Log(title = "股票字典", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Rest remove(@PathVariable Long[] ids)
    {
        return toAjax(stockDictService.deleteStockDictByIds(ids));
    }
}
