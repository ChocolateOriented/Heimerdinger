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
import com.ruoyi.hemerdinger.gpt.domain.GptFictionParagraph;
import com.ruoyi.hemerdinger.gpt.service.IGptFictionParagraphService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 段落Controller
 *
 * @author lijingxiang
 * @date 2024-05-27
 */
@Api("段落管理")
@RestController
@RequestMapping("/gpt/fictionParagraph")
public class GptFictionParagraphController extends BaseController
{
    @Autowired
    private IGptFictionParagraphService gptFictionParagraphService;

    @ApiOperation("查询段落列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('gpt:fictionParagraph:list')")
    @GetMapping("/list")
    public TableDataInfo<List<GptFictionParagraph>> list(GptFictionParagraph gptFictionParagraph)
    {
        startPage();
        List<GptFictionParagraph> list = gptFictionParagraphService.selectGptFictionParagraphList(gptFictionParagraph);
        return getDataTable(list);
    }

    @ApiOperation("导出段落列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('gpt:fictionParagraph:export')")
    @Log(title = "段落", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Rest export(GptFictionParagraph gptFictionParagraph)
    {
        List<GptFictionParagraph> list = gptFictionParagraphService.selectGptFictionParagraphList(gptFictionParagraph);
        ExcelUtil<GptFictionParagraph> util = new ExcelUtil<GptFictionParagraph>(GptFictionParagraph.class);
        return util.exportExcel(list, "段落数据");
    }

    @ApiOperation("获取段落详细信息")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('gpt:fictionParagraph:query')")
    @GetMapping(value = "/{id}")
    public Rest<GptFictionParagraph> getInfo(@PathVariable("id") Long id)
    {
        return Rest.success(gptFictionParagraphService.selectGptFictionParagraphById(id));
    }


    @ApiOperation("新增段落")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('gpt:fictionParagraph:add')")
    @Log(title = "段落", businessType = BusinessType.INSERT)
    @PostMapping
    public Rest<GptFictionParagraph> add(@RequestBody GptFictionParagraph gptFictionParagraph)
    {
        return toAjax(gptFictionParagraphService.insertGptFictionParagraph(gptFictionParagraph));
    }

    @ApiOperation("修改段落")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('gpt:fictionParagraph:edit')")
    @Log(title = "段落", businessType = BusinessType.UPDATE)
    @PutMapping
    public Rest<GptFictionParagraph> edit(@RequestBody GptFictionParagraph gptFictionParagraph)
    {
        return toAjax(gptFictionParagraphService.updateGptFictionParagraph(gptFictionParagraph));
    }

    @ApiOperation("删除段落")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('gpt:fictionParagraph:remove')")
    @Log(title = "段落", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Rest remove(@PathVariable Long[] ids)
    {
        return toAjax(gptFictionParagraphService.deleteGptFictionParagraphByIds(ids));
    }
}
