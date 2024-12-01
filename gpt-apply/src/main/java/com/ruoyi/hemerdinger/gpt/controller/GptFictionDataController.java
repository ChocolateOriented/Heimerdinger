package com.ruoyi.hemerdinger.gpt.controller;

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
import com.ruoyi.hemerdinger.gpt.domain.GptFictionData;
import com.ruoyi.hemerdinger.gpt.service.IGptFictionDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 小说数据Controller
 *
 * @author lijingxiang
 * @date 2024-05-27
 */
@Api("小说数据管理")
@RestController
@RequestMapping("/gpt/fictionData")
public class GptFictionDataController extends BaseController
{
    @Autowired
    private IGptFictionDataService gptFictionDataService;

    @ApiOperation("查询小说数据列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('gpt:fictionData:list')")
    @GetMapping("/list")
    public TableDataInfo<List<GptFictionData>> list(GptFictionData gptFictionData)
    {
        startPage();
        List<GptFictionData> list = gptFictionDataService.selectGptFictionDataList(gptFictionData);
        return getDataTable(list);
    }

    @ApiOperation("导出小说数据列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('gpt:fictionData:export')")
    @Log(title = "小说数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Rest export(GptFictionData gptFictionData)
    {
        List<GptFictionData> list = gptFictionDataService.selectGptFictionDataList(gptFictionData);
        ExcelUtil<GptFictionData> util = new ExcelUtil<GptFictionData>(GptFictionData.class);
        return util.exportExcel(list, "小说数据数据");
    }

    @ApiOperation("获取小说数据详细信息")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('gpt:fictionData:query')")
    @GetMapping(value = "/{id}")
    public Rest<GptFictionData> getInfo(@PathVariable("id") Long id)
    {
        return Rest.success(gptFictionDataService.selectGptFictionDataById(id));
    }


    @ApiOperation("新增小说数据")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('gpt:fictionData:add')")
    @Log(title = "小说数据", businessType = BusinessType.INSERT)
    @PostMapping
    public Rest<GptFictionData> add(@RequestBody GptFictionData gptFictionData)
    {
        return toAjax(gptFictionDataService.insertGptFictionData(gptFictionData));
    }

    @ApiOperation("修改小说数据")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('gpt:fictionData:edit')")
    @Log(title = "小说数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public Rest<GptFictionData> edit(@RequestBody GptFictionData gptFictionData)
    {
        return toAjax(gptFictionDataService.updateGptFictionData(gptFictionData));
    }

    @ApiOperation("删除小说数据")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('gpt:fictionData:remove')")
    @Log(title = "小说数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Rest remove(@PathVariable Long[] ids)
    {
        return toAjax(gptFictionDataService.deleteGptFictionDataByIds(ids));
    }
}
