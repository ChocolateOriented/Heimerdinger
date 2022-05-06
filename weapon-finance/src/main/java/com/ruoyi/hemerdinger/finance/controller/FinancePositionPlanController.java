package com.ruoyi.hemerdinger.finance.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hemerdinger.finance.domain.StockPositionPlan;
import com.ruoyi.hemerdinger.finance.domain.vo.TradeAdviceListVo;
import com.ruoyi.hemerdinger.finance.domain.vo.TradeAdviceType;
import com.ruoyi.hemerdinger.finance.manager.StockManager;
import com.ruoyi.hemerdinger.finance.service.IStockDataConfigService;
import com.ruoyi.hemerdinger.finance.service.IStockPositionPlanService;
import com.ruoyi.hemerdinger.finance.service.IStockTraceService;
import com.ruoyi.hemerdinger.finance.service.impl.StockDataConfigServiceImpl;
import com.ruoyi.hemerdinger.finance.service.impl.StockTraceServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.ruoyi.hemerdinger.finance.domain.FinancePositionPlan;
import com.ruoyi.hemerdinger.finance.service.IFinancePositionPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 持仓计划Controller
 *
 * @author lijing xiang
 * @date 2022-04-27
 */
@Api("持仓计划管理")
@RestController
@RequestMapping("/finance/financePositionPlan")
public class FinancePositionPlanController extends BaseController
{
    @Autowired
    private IFinancePositionPlanService financePositionPlanService;
    @Autowired
    private IStockPositionPlanService stockPositionPlanService;
    @Autowired
    private IStockTraceService stockTraceService;

    @ApiOperation("查询持仓计划列表")
    @ApiOperationSupport(author = "lijing xiang")
    @PreAuthorize("@ss.hasPermi('finance:financePositionPlan:list')")
    @GetMapping("/list")
    public TableDataInfo<List<FinancePositionPlan>> list(FinancePositionPlan financePositionPlan)
    {
        startPage();
        List<FinancePositionPlan> list = financePositionPlanService.selectFinancePositionPlanList(financePositionPlan);
        return getDataTable(list);
    }

    @ApiOperation("交易建议列表")
    @ApiOperationSupport(author = "lijing xiang")
    @PreAuthorize("@ss.hasPermi('finance:financePositionPlan:list')")
    @GetMapping("/tradeAdviceList")
    public Rest<List<TradeAdviceListVo>> tradeAdviceList(FinancePositionPlan financePositionPlan)
    {
        List<FinancePositionPlan> list = financePositionPlanService.selectFinancePositionPlanList(financePositionPlan);
        List<TradeAdviceListVo> adviceListVos = new ArrayList<>();
        Date now = DateUtils.dateTime(DateUtils.YYYY_MM_DD, DateUtils.getDate());
        for (int i = 0; i < list.size(); i++) {
            FinancePositionPlan positionPlan = list.get(i);
            Long traceId = positionPlan.getTraceId();
            StockPositionPlan query = new StockPositionPlan();
            query.setTraceId(traceId);
            List<StockPositionPlan> stockPositionPlans = stockPositionPlanService.selectStockPositionPlanList(query);
            if (null == traceId){
                continue;
            }
            String code = stockTraceService.selectStockTraceById(traceId).getCode();
            JSONObject currentInfo = stockTraceService.findCurrentInfo(code);
            BigDecimal currentPrice = currentInfo.getBigDecimal(IStockDataConfigService.NAME_PRICE);
            BigDecimal realityAmount = positionPlan.getRealityAmount();

            TradeAdviceListVo tradeAdvice = null;
            for (int j = 0; j < stockPositionPlans.size(); j++) {
                StockPositionPlan stockPositionPlan =  stockPositionPlans.get(j);

                //价格触发
                BigDecimal griddingAdvicePrice = stockPositionPlan.getAdvicePrice();
                BigDecimal griddingAdviceAmount =stockPositionPlan.getGriddingAmount();
                boolean griddingAmountShort = realityAmount.compareTo(griddingAdviceAmount) < 0;

                if (griddingAdvicePrice.compareTo(currentPrice) >= 0 && griddingAmountShort){
                    tradeAdvice = new TradeAdviceListVo(traceId,positionPlan.getName(),currentPrice, TradeAdviceType.BUY_PRICE,
                        griddingAdviceAmount,realityAmount);
                    continue;
                }

                Date adviceDate = stockPositionPlan.getAdviceDate();
                BigDecimal timeAdviceAmount = stockPositionPlan.getAdviceAmount();
                boolean timeAmountShort = realityAmount.compareTo(timeAdviceAmount) < 0;
                //时间触发
                if (adviceDate.compareTo(now) <= 0 && timeAmountShort ){
                    tradeAdvice = new TradeAdviceListVo(traceId,positionPlan.getName(),currentPrice, TradeAdviceType.BUY_DATE,
                            timeAdviceAmount,realityAmount);
                    continue;
                }

            }
            if (null != tradeAdvice){
                adviceListVos.add(tradeAdvice);
            }
        }
        return Rest.success(adviceListVos);
    }

    @ApiOperation("导出持仓计划列表")
    @ApiOperationSupport(author = "lijing xiang")
    @PreAuthorize("@ss.hasPermi('finance:financePositionPlan:export')")
    @Log(title = "持仓计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Rest export(FinancePositionPlan financePositionPlan)
    {
        List<FinancePositionPlan> list = financePositionPlanService.selectFinancePositionPlanList(financePositionPlan);
        ExcelUtil<FinancePositionPlan> util = new ExcelUtil<FinancePositionPlan>(FinancePositionPlan.class);
        return util.exportExcel(list, "持仓计划数据");
    }

    @ApiOperation("获取持仓计划详细信息")
    @ApiOperationSupport(author = "lijing xiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('finance:financePositionPlan:query')")
    @GetMapping(value = "/{id}")
    public Rest<FinancePositionPlan> getInfo(@PathVariable("id") Long id)
    {
        return Rest.success(financePositionPlanService.selectFinancePositionPlanById(id));
    }


    @ApiOperation("新增持仓计划")
    @ApiOperationSupport(author = "lijing xiang")
    @PreAuthorize("@ss.hasPermi('finance:financePositionPlan:add')")
    @Log(title = "持仓计划", businessType = BusinessType.INSERT)
    @PostMapping
    public Rest<FinancePositionPlan> add(@RequestBody FinancePositionPlan financePositionPlan)
    {
        return toAjax(financePositionPlanService.insertFinancePositionPlan(financePositionPlan));
    }

    @ApiOperation("修改持仓计划")
    @ApiOperationSupport(author = "lijing xiang")
    @PreAuthorize("@ss.hasPermi('finance:financePositionPlan:edit')")
    @Log(title = "持仓计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public Rest<FinancePositionPlan> edit(@RequestBody FinancePositionPlan financePositionPlan)
    {
        return toAjax(financePositionPlanService.updateFinancePositionPlan(financePositionPlan));
    }

    @ApiOperation("删除持仓计划")
    @ApiOperationSupport(author = "lijing xiang")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @PreAuthorize("@ss.hasPermi('finance:financePositionPlan:remove')")
    @Log(title = "持仓计划", businessType = BusinessType.DELETE)
	  @DeleteMapping("/{ids}")
    public Rest remove(@PathVariable Long[] ids)
    {
        return toAjax(financePositionPlanService.deleteFinancePositionPlanByIds(ids));
    }


}
