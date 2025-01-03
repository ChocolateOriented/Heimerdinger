package com.ruoyi.dev;

import org.junit.Test;


public class Simple {
    @Test
    public void saveOrUpdateIndicator() {
        // 字符串占位符替换
        String str = "http://www.baidu.com/s?wd=%s";
        System.out.println(String.format(str, "hello"));

        System.out.printf(2.2 / 2 + "");
    }

}
