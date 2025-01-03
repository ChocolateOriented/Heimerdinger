package com.ruoyi.hemerdinger.finance.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Rest;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hemerdinger.finance.domain.StockDict;
import com.ruoyi.hemerdinger.finance.domain.StockReport;
import com.ruoyi.hemerdinger.finance.domain.vo.StockSubjectListVo;
import com.ruoyi.hemerdinger.finance.service.IStockDictService;
import com.ruoyi.hemerdinger.finance.service.IStockReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 概念相关股票Controller
 *
 * @author lijingxiang
 * @date 2024-12-10
 */
@Api("概念相关股票")
@RestController
@RequestMapping("/finance/stockSubject")
public class StockSubjectController extends BaseController
{

    @Autowired
    private IStockReportService stockReportService;

    @ApiOperation("查询相关概念股票")
    @ApiOperationSupport(author = "lijingxiang")
    @GetMapping("/list")
    public Rest<List<StockSubjectListVo>> list(String keyword) {
        return Rest.success(stockReportService.list(keyword));
    }

    @ApiOperation("查询股票经营报告")
    @ApiOperationSupport(author = "lijingxiang")
    @GetMapping("/reportList")
    public Rest<List<StockReport>> reportList(String code) {
        return Rest.success(stockReportService.reportList(code));
    }
}
