package com.ruoyi.hemerdinger.finance.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.UpdateResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.hemerdinger.finance.domain.vo.LineReq;
import com.ruoyi.hemerdinger.finance.manager.AkShareManager;
import com.ruoyi.hemerdinger.finance.service.IIndicatorService;
import com.ruoyi.hemerdinger.finance.util.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.*;

import static com.ruoyi.common.utils.DateUtils.YYYYMMDD;

/**
 * 股票追踪Service业务层处理
 *
 * @author lijingxiang
 * @date 2022-04-24
 */
@Service("indicatorService")
public class IndicatorServiceImpl implements IIndicatorService {
    private static final Logger log = LoggerFactory.getLogger(IndicatorServiceImpl.class);

    @Autowired
    private AkShareManager akShareManager;
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String COLLECTION_INDICATOR = "indicator";
//    @PostConstruct
//    private void init() {
//        akShareUpdataIndicators = new ArrayList<>();
//
//        akShareUpdataIndicators.add(new akShareUpdataIndicator("macro_china_pmi_yearly", MacroChinaPmiYearly.class, macroChinaPmiYearlyRepository));
//        akShareUpdataIndicators.add(new akShareUpdataIndicator("macro_china_ppi_yearly", MacroChinaPpiYearly.class, macroChinaPpiYearlyRepository));
//        akShareUpdataIndicators.add(new akShareUpdataIndicator("stock_a_ttm_lyr", StockATtmLyr.class, stockATtmLyrRepository));
//        akShareUpdataIndicators.add(new akShareUpdataIndicator("macro_china_cpi_yearly", MacroChinaCpiYearly.class, macroChinaCpiYearlyRepository));
//        akShareUpdataIndicators.add(new akShareUpdataIndicator("macro_china_supply_of_money", MacroChinaSupplyOfMoney.class, macroChinaSupplyOfMoneyRepository));
//        akShareUpdataIndicators.add(new akShareUpdataIndicator("stock_a_all_pb", StockAAllPb.class, stockAAllPbRepository));
//        akShareUpdataIndicators.add(new akShareUpdataIndicator("bond_zh_us_rate", BondZhUsRate.class, bondZhUsRateRepository));
//    }

    /**
     * 每日更新指标
     */
    @Override
    public void updateDaysIndicators() {
        // 货币供应
        this.updateSupplyMoney();
        this.updateChinaGdp();
        this.updateChinaCPI();
        // 融资余额
        this.updateStockMarginSse();
        // 国债收益率
        this.updateBondZhUsRate();
        this.updateMacroChinaPmiYearly();
        this.updateMacroChinaPpiYearly();
        // 市场指数
        this.updateShangZhengZhiShu();
        this.updateHusheng300();
        this.updateChuangYeBanZhiShu();
        // 万得全A市盈率
        this.updateIndexValueHistFunddb();
    }

    /**
     * 保存或更新指标
     *
     * @param dataList
     */
    public void saveOrUpdateIndicator(List<JSONObject> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        for (int i = 0; i < dataList.size(); i++) {
            JSONObject jsonObject = dataList.get(i);
            String date = jsonObject.getString("date");
            Query query = new Query(Criteria.where("date").is(date));
            JSONObject one = mongoTemplate.findOne(query, JSONObject.class, COLLECTION_INDICATOR);

            boolean equal = true;
            if (one == null) {
                //新增数据
                mongoTemplate.insert(jsonObject, COLLECTION_INDICATOR);
                continue;
            }
            //已存在进行更新
            Set<String> keys = jsonObject.keySet();
            Update update = new Update();
            for (String key : keys) {
                if ("date" == key) {
                    continue;
                }
                String newValue = jsonObject.getString(key);
                String oldValue = one.getString(key);
                if (!StringUtils.equals(newValue, oldValue)) {
                    equal = false;
                    update.set(key, newValue);
                }
            }
            if (equal) {//如果数据相同就不做处理
                continue;
            }
            //更新查询满足条件的文档数据（第一条）
            UpdateResult result = mongoTemplate.updateFirst(query, update, JSONObject.class, COLLECTION_INDICATOR);
            if (result != null) {
                log.info("更新条数：{}", result.getMatchedCount());
            }
        }
    }

    /**
     * 执行更新指标
     *
     * @param akSharePath   akshare的接口路径
     * @param formater      格式化日期
     * @param key2FieldName 指标值与字段名映射
     */
    public void doUpdateIndicators(String akSharePath, Map<String, String> param, IndicatorDateFormater formater, HashMap<String, String> key2FieldName) {
        String result = akShareManager.find(akSharePath, param);
        JSONArray jsonArray = JSON.parseArray(result);
        if (CollectionUtils.isEmpty(jsonArray)) {
            return;
        }
        List<JSONObject> dataList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            JSONObject data = new JSONObject();
            // 日期处理
            Date date = null;
            try {
                date = formater.format(o);
            } catch (ParseException e) {
                log.error("日期格式化异常: " + o.toJSONString(), e);
            }
            String dateStr = DateUtils.dateTime(date);
            data.put("date", dateStr);
            // 指标值与字段名映射
            for (String key : key2FieldName.keySet()) {
                data.put(key2FieldName.get(key), o.getString(key));
            }
            dataList.add(data);
        }
        this.saveOrUpdateIndicator(dataList);
    }

    public void doUpdateIndicators(String akSharePath, IndicatorDateFormater formater, HashMap<String, String> key2FieldName) {
        this.doUpdateIndicators(akSharePath, null, formater, key2FieldName);
    }

    /**
     * 每月更新指标
     */
    @Override
    public void updateMonthsIndicators() {

    }

    /**
     * 从mongo中查询指标数据
     *
     * @param req
     * @return
     */
    @Override
//    @Cacheable(value = "findFromMongo", key = "#req", unless = "#result==null")
    public List<JSONObject> findFromMongo(LineReq req) {
        List<String> reqColumns = req.getColumns();
        // 复制列表
        List<String> columns = new ArrayList<>(reqColumns);
        columns.add("date");
        //list 转数组
        String[] columnsArray = columns.toArray(new String[0]);
        String startTime = req.getStartTime();
        String endTime = req.getEndTime();
        // 从mongo集合indicator中查询指定列, 获取指定日期范围内的数据
        Query query = new Query();
        // 若起始日期不为空
        if (StringUtils.isNotBlank(startTime)) {
            query.addCriteria(Criteria.where("date").gte(startTime));
        }
        if (StringUtils.isNotBlank(endTime)) {
            query.addCriteria(Criteria.where("date").lte(endTime));
        }
        query.with(Sort.by(Sort.Direction.ASC, "date"));
        query.fields().include(columnsArray);
        List<JSONObject> result = mongoTemplate.find(query, JSONObject.class, COLLECTION_INDICATOR);
        result = DataUtils.fillMissingDatesAndInterpolate(result, reqColumns);
        return result;
    }


    interface IndicatorDateFormater {
        Date format(JSONObject o) throws ParseException;
    }

    public void updateSupplyMoney() {
        HashMap<String, String> key2FieldName = new HashMap<>();
        key2FieldName.put("货币(狭义货币M1)同比增长", "货币(狭义货币M1)同比增长");
        key2FieldName.put("货币和准货币（广义货币M2）同比增长", "货币和准货币（广义货币M2）同比增长");
        doUpdateIndicators("macro_china_supply_of_money", (IndicatorDateFormater) o -> {
            String dateStr = o.getString("统计时间") + ".10";
            Date date = DateUtils.parseDate(dateStr, "yyyy.MM.dd");
            return date;
        }, key2FieldName);
    }


    public void updateChinaGdp() {
        HashMap<String, String> key2FieldName = new HashMap<>();
        key2FieldName.put("今值", "GDP");
        key2FieldName.put("预测值", "GDP预测值");
        this.doUpdateIndicators("macro_china_gdp_yearly", o -> {
            String dateStr = o.getString("日期");
            dateStr = dateStr.replace("T", " ");
            Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
            return date;
        }, key2FieldName);

    }

    public void updateChinaCPI() {
        HashMap<String, String> key2FieldName = new HashMap<>();
        key2FieldName.put("今值", "CPI");
        key2FieldName.put("预测值", "CPI预测值");
        this.doUpdateIndicators("macro_china_cpi_yearly", o -> {
            String dateStr = o.getString("日期");
            dateStr = dateStr.replace("T", " ");
            Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
            return date;
        }, key2FieldName);
    }

    public void updateStockMarginSse() {
        HashMap<String, String> key2FieldName = new HashMap<>();
        key2FieldName.put("融资余额", "融资余额");
        //循环10次
        for (int i = 0; i < 10; i++) {
            Map<String, String> param = new HashMap<>();
            // 从mongo中查出最近一条, 获取其日期
            List<JSONObject> jsonObjects = mongoTemplate.find(new Query()
                    .with(Sort.by(Sort.Direction.DESC, "date"))
                    .addCriteria(Criteria.where("融资余额").ne(null))
                    .limit(1), JSONObject.class, COLLECTION_INDICATOR);
            String startDate = null;
            if (CollectionUtils.isEmpty(jsonObjects)) {
                startDate = "2010-03-30";
            } else {
                startDate = jsonObjects.get(0).getString("date");
            }
            // 查询跨度最多三年
            Date start = DateUtils.parseDate(startDate);
            Date now = new Date();
            //如果startDate为12月29号, 则改为下一年的1月1日
            if (start.getDate() == 29 && start.getMonth() == Calendar.DECEMBER) {
                start = DateUtils.addDays(start, 3);
            }
            if (start.after(now)) {
                return;
            }
            Date endDate = DateUtils.addMonths(start, 36);
            // 与当前日期比较
            if (now.before(endDate)) {
                endDate = now;
            }
            param.put("start_date", DateUtils.dateTime(start));
            param.put("end_date", DateUtils.dateTime(endDate));

            this.doUpdateIndicators("stock_margin_sse", param, o -> {
                String dateStr = o.getString("信用交易日期");
                Date date = DateUtils.parseDate(dateStr, YYYYMMDD);
                return date;
            }, key2FieldName);
        }
    }

    public void updateBondZhUsRate() {
        HashMap<String, String> key2FieldName = new HashMap<>();
        key2FieldName.put("中国国债收益率10年", "中国国债收益率10年");
        key2FieldName.put("美国国债收益率10年", "美国国债收益率10年");
        this.doUpdateIndicators("bond_zh_us_rate", o -> {
            String dateStr = o.getString("日期");
            dateStr = dateStr.replace("T", " ");
            Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
            return date;
        }, key2FieldName);
    }

    public void updateMacroChinaPmiYearly() {
        HashMap<String, String> key2FieldName = new HashMap<>();
        key2FieldName.put("今值", "中国官方制造业PMI");
        this.doUpdateIndicators("macro_china_pmi_yearly", o -> {
            String dateStr = o.getString("日期");
            dateStr = dateStr.replace("T", " ");
            Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
            return date;
        }, key2FieldName);
    }

    public void updateMacroChinaPpiYearly() {
        HashMap<String, String> key2FieldName = new HashMap<>();
        key2FieldName.put("今值", "中国PPI");
        this.doUpdateIndicators("macro_china_ppi_yearly", o -> {
            String dateStr = o.getString("日期");
            dateStr = dateStr.replace("T", " ");
            Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
            return date;
        }, key2FieldName);
    }

    public void updateShangZhengZhiShu() {
        HashMap<String, String> key2FieldName = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        key2FieldName.put("close", "上证指数");
        param.put("symbol", "sh000001");
        this.doUpdateIndicators("stock_zh_index_daily", param, o -> {
            String dateStr = o.getString("date");
            dateStr = dateStr.replace("T", " ");
            Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
            return date;
        }, key2FieldName);
    }


    public void updateHusheng300() {
        HashMap<String, String> key2FieldName = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        key2FieldName.put("close", "沪深300");
        param.put("symbol", "sh000300");
        this.doUpdateIndicators("stock_zh_index_daily", param, o -> {
            String dateStr = o.getString("date");
            dateStr = dateStr.replace("T", " ");
            Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
            return date;
        }, key2FieldName);
    }

    public void updateChuangYeBanZhiShu() {
        HashMap<String, String> key2FieldName = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        key2FieldName.put("close", "创业板");
        param.put("symbol", "sz399006");
        this.doUpdateIndicators("stock_zh_index_daily", param, o -> {
            String dateStr = o.getString("date");
            dateStr = dateStr.replace("T", " ");
            Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
            return date;
        }, key2FieldName);
    }

    public void updateIndexValueHistFunddb() {
        HashMap<String, String> key2FieldName = new HashMap<>();
        key2FieldName.put("市盈率", "万得全A市盈率");
        this.doUpdateIndicators("index_value_hist_funddb", o -> {
            String dateStr = o.getString("日期");
            dateStr = dateStr.replace("T", " ");
            Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
            return date;
        }, key2FieldName);
    }
}