package com.ruoyi.dev;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.hemerdinger.finance.service.IIndicatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = RuoYiApplication.class)
@RunWith(SpringRunner.class)
public class IndicatorTest {

    @Autowired
    IIndicatorService indicatorService;

    @Test
    public void testUpdateIndicator(){
        indicatorService.updateIndicators();
    }

    @Test
    public void saveOrUpdateIndicator(){
        List<JSONObject> dataList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date","2023-01-01");
        jsonObject.put("valuetest",124412343);
        dataList.add(jsonObject);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("date","2023-01-03");
        jsonObject2.put("fttt",23222);
        dataList.add(jsonObject2);
        indicatorService.saveOrUpdateIndicator(dataList);
    }



}
