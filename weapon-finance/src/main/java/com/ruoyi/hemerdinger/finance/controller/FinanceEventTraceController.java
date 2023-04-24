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
import com.ruoyi.hemerdinger.finance.domain.FinanceEventTrace;
import com.ruoyi.hemerdinger.finance.service.IFinanceEventTraceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 经济事件追踪Controller
 *
 * @author lijingxiang
 * @date 2022-05-11
 */
@Api("经济事件追踪管理")
@RestController
@RequestMapping("/finance/financeEventTrace")
public class FinanceEventTraceController extends BaseController
{
    @Autowired
    private IFinanceEventTraceService financeEventTraceService;

    @ApiOperation("查询经济事件追踪列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:financeEventTrace:list')")
    @GetMapping("/list")
    public TableDataInfo<List<FinanceEventTrace>> list(FinanceEventTrace financeEventTrace)
    {
        startPage();
        List<FinanceEventTrace> list = financeEventTraceService.selectFinanceEventTraceList(financeEventTrace);
        return getDataTable(list);
    }

    @ApiOperation("导出经济事件追踪列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:financeEventTrace:export')")
    @Log(title = "经济事件追踪", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Rest export(FinanceEventTrace financeEventTrace)
    {
        List<FinanceEventTrace> list = financeEventTraceService.selectFinanceEventTraceList(financeEventTrace);
        ExcelUtil<FinanceEventTrace> util = new ExcelUtil<FinanceEventTrace>(FinanceEventTrace.class);
        return util.exportExcel(list, "经济事件追踪数据");
    }

    @ApiOperation("获取经济事件追踪详细信息")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('finance:financeEventTrace:query')")
    @GetMapping(value = "/{id}")
    public Rest<FinanceEventTrace> getInfo(@PathVariable("id") Long id)
    {
        return Rest.success(financeEventTraceService.selectFinanceEventTraceById(id));
    }


    @ApiOperation("新增经济事件追踪")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:financeEventTrace:add')")
    @Log(title = "经济事件追踪", businessType = BusinessType.INSERT)
    @PostMapping
    public Rest<FinanceEventTrace> add(@RequestBody FinanceEventTrace financeEventTrace)
    {
        return toAjax(financeEventTraceService.insertFinanceEventTrace(financeEventTrace));
    }

    @ApiOperation("修改经济事件追踪")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:financeEventTrace:edit')")
    @Log(title = "经济事件追踪", businessType = BusinessType.UPDATE)
    @PutMapping
    public Rest<FinanceEventTrace> edit(@RequestBody FinanceEventTrace financeEventTrace)
    {
        return toAjax(financeEventTraceService.updateFinanceEventTrace(financeEventTrace));
    }

    @ApiOperation("删除经济事件追踪")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('finance:financeEventTrace:remove')")
    @Log(title = "经济事件追踪", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Rest remove(@PathVariable Long[] ids)
    {
        return toAjax(financeEventTraceService.deleteFinanceEventTraceByIds(ids));
    }
}
