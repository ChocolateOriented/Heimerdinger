package com.ruoyi.hemerdinger.finance.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Rest;
import com.ruoyi.hemerdinger.finance.domain.vo.AkShareReq;
import com.ruoyi.hemerdinger.finance.domain.vo.LineReq;
import com.ruoyi.hemerdinger.finance.manager.AkShareManager;
import com.ruoyi.hemerdinger.finance.repository.StockAAllPbRepository;
import com.ruoyi.hemerdinger.finance.service.IIndicatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * akShare 通用
 * @author lijing xiang
 * @date 2022-04-27
 */
@Api("AKShare通用")
@RestController
@RequestMapping("/ak_share")
public class AKShareController extends BaseController
{

    @Autowired
    private AkShareManager akShareManager;

    @Autowired
    private IIndicatorService indicatorService;

    @ApiOperation("查询")
    @ApiOperationSupport(author = "lijing xiang")
    @GetMapping("/find")
    public Rest<JSONArray> find(AkShareReq req) {
        return Rest.success( JSON.parseArray(akShareManager.find(req.getPath(),req.getParam())));
    }

    @ApiOperation("查询")
    @ApiOperationSupport(author = "lijing xiang")
    @PostMapping("/findLineFromMongo")
    public Rest<List<JSONObject>> findFromMongo(@RequestBody LineReq lineReq) {
        return Rest.success( indicatorService.findFromMongo(lineReq));
    }
}
