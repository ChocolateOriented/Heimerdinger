package com.ruoyi.dev;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.hemerdinger.finance.service.IIndicatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = RuoYiApplication.class)
@RunWith(SpringRunner.class)
public class IndicatorTest {

    @Autowired
    IIndicatorService indicatorService;

    @Test
    public void testUpdateIndicator(){
        indicatorService.updateIndicators();
    }
}
