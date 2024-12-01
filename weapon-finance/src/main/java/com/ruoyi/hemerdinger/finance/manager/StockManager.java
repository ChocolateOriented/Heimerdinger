package com.ruoyi.hemerdinger.finance.manager;

import com.ruoyi.common.utils.http.HttpUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
		HashMap<String, String> header = new HashMap<>();
		header.put("Referer","https://finance.sina.com.cn/");
		return HttpUtils.sendGet("https://hq.sinajs.cn/etag.php?_="+new Date().getTime() +"&list=" +code, null,header);
	}

	public static void main(String[] args) {
		HashMap<String, String> header = new HashMap<>();
		header.put("Referer","https://finance.sina.com.cn/");
		System.out.println( HttpUtils.sendGet("https://hq.sinajs.cn/etag.php?_="+new Date().getTime() +"&list=" +"sh603501" , null,header));
	}
}

