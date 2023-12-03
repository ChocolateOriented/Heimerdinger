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
import com.ruoyi.hemerdinger.finance.domain.TraceLog;
import com.ruoyi.hemerdinger.finance.service.ITraceLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 追踪日志Controller
 *
 * @author lijingxiang
 * @date 2023-11-30
 */
@Api("追踪日志管理")
@RestController
@RequestMapping("/finance/traceLog")
public class TraceLogController extends BaseController
{
    @Autowired
    private ITraceLogService traceLogService;

    @ApiOperation("查询追踪日志列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:traceLog:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TraceLog>> list(TraceLog traceLog)
    {
        startPage();
        List<TraceLog> list = traceLogService.selectTraceLogList(traceLog);
        return getDataTable(list);
    }

    @ApiOperation("导出追踪日志列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:traceLog:export')")
    @Log(title = "追踪日志", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Rest export(TraceLog traceLog)
    {
        List<TraceLog> list = traceLogService.selectTraceLogList(traceLog);
        ExcelUtil<TraceLog> util = new ExcelUtil<TraceLog>(TraceLog.class);
        return util.exportExcel(list, "追踪日志数据");
    }

    @ApiOperation("获取追踪日志详细信息")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('finance:traceLog:query')")
    @GetMapping(value = "/{id}")
    public Rest<TraceLog> getInfo(@PathVariable("id") Long id)
    {
        return Rest.success(traceLogService.selectTraceLogById(id));
    }


    @ApiOperation("新增追踪日志")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:traceLog:add')")
    @Log(title = "追踪日志", businessType = BusinessType.INSERT)
    @PostMapping
    public Rest<TraceLog> add(@RequestBody TraceLog traceLog)
    {
        return toAjax(traceLogService.insertTraceLog(traceLog));
    }

    @ApiOperation("修改追踪日志")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('finance:traceLog:edit')")
    @Log(title = "追踪日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public Rest<TraceLog> edit(@RequestBody TraceLog traceLog)
    {
        return toAjax(traceLogService.updateTraceLog(traceLog));
    }

    @ApiOperation("删除追踪日志")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('finance:traceLog:remove')")
    @Log(title = "追踪日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Rest remove(@PathVariable Long[] ids)
    {
        return toAjax(traceLogService.deleteTraceLogByIds(ids));
    }
}
