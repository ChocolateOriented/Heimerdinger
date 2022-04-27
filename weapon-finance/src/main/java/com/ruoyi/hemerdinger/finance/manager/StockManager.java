package com.ruoyi.hemerdinger.finance.manager;

import com.ruoyi.common.utils.http.HttpUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @File: StockManager
 * @Version: 1.0
 * @Description:
 * @Author: lijingxiang
 * @Date: 2022/4/25 16:55
 */
@Service
public class StockManager {
//	http://qt.gtimg.cn/q=sh600519
	public String findStock(String code){
		return HttpUtils.sendGet("http://qt.gtimg.cn/q=" +code,null);
	}
}

