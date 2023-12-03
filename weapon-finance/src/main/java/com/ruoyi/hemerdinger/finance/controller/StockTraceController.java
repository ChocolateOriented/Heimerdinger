package com.ruoyi.hemerdinger.finance.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Rest;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hemerdinger.finance.domain.StockTrace;
import com.ruoyi.hemerdinger.finance.domain.vo.TradeGradeVo;
import com.ruoyi.hemerdinger.finance.service.IStockDataConfigService;
import com.ruoyi.hemerdinger.finance.service.IStockTraceService;
import com.ruoyi.hemerdinger.finance.util.DecimalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 股票追踪Controller
 *
 * @author lijingxiang
 * @date 2023-11-26
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

    @ApiOperation("交易机会评分排名")
    @ApiOperationSupport(author = "lijingxiang")
    @GetMapping("/tradeGradeList")
    public Rest<List<TradeGradeVo>> tradeGradeList(StockTrace stockTrace)
    {
        List<StockTrace> list = stockTraceService.selectStockTraceList(stockTrace);
        List<TradeGradeVo> tradeGradeVoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            StockTrace trace =  list.get(i);
            String code = trace.getCode();
            if(StringUtils.isBlank(code)){
                continue;
            }
            BigDecimal currentPrice = stockTraceService.findCurrentInfo(code).getBigDecimal(IStockDataConfigService.NAME_PRICE);
            BigDecimal costPrice = trace.getCostPrice();
            BigDecimal assessmen = trace.getAssessmen();
            BigDecimal assessmenFit = trace.getAssessmenFit();
            BigDecimal assessmenMin = trace.getAssessmenMin();

            BigDecimal currentAssessmen = DecimalUtil.div(currentPrice,(DecimalUtil.div(costPrice,assessmen)));
            BigDecimal planRise = DecimalUtil.div(assessmenFit.subtract(currentAssessmen), currentAssessmen).multiply(new BigDecimal(100));
            BigDecimal planFall = DecimalUtil.div(currentAssessmen.subtract(assessmenMin), currentAssessmen).multiply(new BigDecimal(100));
            BigDecimal grade = DecimalUtil.div(planRise,planFall.multiply(new BigDecimal(2)));

            TradeGradeVo tradeGradeVo = new TradeGradeVo(trace.getId(),trace.getName(),currentPrice,planRise,planFall,grade);
            tradeGradeVoList.add(tradeGradeVo);
        }
        return Rest.success(tradeGradeVoList);
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
