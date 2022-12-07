package com.ruoyi.hemerdinger.finance.domain.vo;

import java.util.Map;

public class AkShareReq {

    private String path;
    private Map<String,String> param;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }
}
