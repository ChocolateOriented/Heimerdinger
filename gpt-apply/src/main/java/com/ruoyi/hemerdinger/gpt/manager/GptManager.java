package com.ruoyi.hemerdinger.gpt.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.hemerdinger.gpt.domain.bo.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GptManager {

    @Value("${gpt.tongyi.api}")
    private String tongyiApi;

    @Value("${gpt.tongyi.key}")
    private String tonyiApiKey;

//    @Cacheable(value = "tongyi", key = "#path+':'+#param", unless = "#result==null")
    public String call(List<Message> messages) {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", tonyiApiKey);
        header.put("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", "qwen-long");
        jsonObject.put("messages", messages);

        String response = HttpUtils.sendPost(tongyiApi , jsonObject.toJSONString() , null);
        JSONObject jsonResponse = JSON.parseObject(response);
        return jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
    }
}
