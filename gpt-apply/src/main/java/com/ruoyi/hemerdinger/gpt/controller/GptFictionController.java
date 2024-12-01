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
import com.ruoyi.hemerdinger.gpt.domain.GptFiction;
import com.ruoyi.hemerdinger.gpt.service.IGptFictionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 小说Controller
 *
 * @author lijingxiang
 * @date 2024-05-27
 */
@Api("小说管理")
@RestController
@RequestMapping("/gpt/fiction")
public class GptFictionController extends BaseController
{
    @Autowired
    private IGptFictionService gptFictionService;

    @ApiOperation("查询小说列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('gpt:fiction:list')")
    @GetMapping("/list")
    public TableDataInfo<List<GptFiction>> list(GptFiction gptFiction)
    {
        startPage();
        List<GptFiction> list = gptFictionService.selectGptFictionList(gptFiction);
        return getDataTable(list);
    }

    @ApiOperation("导出小说列表")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('gpt:fiction:export')")
    @Log(title = "小说", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Rest export(GptFiction gptFiction)
    {
        List<GptFiction> list = gptFictionService.selectGptFictionList(gptFiction);
        ExcelUtil<GptFiction> util = new ExcelUtil<GptFiction>(GptFiction.class);
        return util.exportExcel(list, "小说数据");
    }

    @ApiOperation("获取小说详细信息")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('gpt:fiction:query')")
    @GetMapping(value = "/{id}")
    public Rest<GptFiction> getInfo(@PathVariable("id") Long id)
    {
        return Rest.success(gptFictionService.selectGptFictionById(id));
    }


    @ApiOperation("新增小说")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('gpt:fiction:add')")
    @Log(title = "小说", businessType = BusinessType.INSERT)
    @PostMapping
    public Rest<GptFiction> add(@RequestBody GptFiction gptFiction)
    {
        return toAjax(gptFictionService.insertGptFiction(gptFiction));
    }


    @ApiOperation("创建小说")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('gpt:fiction:add')")
    @Log(title = "小说", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    public Rest<GptFiction> create(@RequestBody GptFiction gptFiction)
    {
        return toAjax(gptFictionService.createGptFiction(gptFiction));
    }

    @ApiOperation("修改小说")
    @ApiOperationSupport(author = "lijingxiang")
    @PreAuthorize("@ss.hasPermi('gpt:fiction:edit')")
    @Log(title = "小说", businessType = BusinessType.UPDATE)
    @PutMapping
    public Rest<GptFiction> edit(@RequestBody GptFiction gptFiction)
    {
        return toAjax(gptFictionService.updateGptFiction(gptFiction));
    }

    @ApiOperation("删除小说")
    @ApiOperationSupport(author = "lijingxiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('gpt:fiction:remove')")
    @Log(title = "小说", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Rest remove(@PathVariable Long[] ids)
    {
        return toAjax(gptFictionService.deleteGptFictionByIds(ids));
    }
}
