package com.ruoyi.hemerdinger.finance.manager;

import com.ruoyi.common.utils.http.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class AkShareManager {

    @Value("${akShare.api}")
    private String akShareApi;


    @Cacheable(value = "akShare", key = "#path+':'+#param", unless = "#result==null")
    public String find(String path, Map<String,String> param) {
        return HttpUtils.sendGet(akShareApi+"/api/public/"+path , param);
    }
}
