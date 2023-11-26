package com.ruoyi.hemerdinger.finance.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.hemerdinger.finance.domain.indicator.*;
import com.ruoyi.hemerdinger.finance.manager.AkShareManager;
import com.ruoyi.hemerdinger.finance.repository.*;
import com.ruoyi.hemerdinger.finance.service.IIndicatorService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 股票追踪Service业务层处理
 *
 * @author lijingxiang
 * @date 2022-04-24
 */
@Service
public class IndicatorServiceImpl implements IIndicatorService {
    private static final Logger log = LoggerFactory.getLogger(IndicatorServiceImpl.class);

    @Autowired
    private StockAAllPbRepository stockAAllPbRepository;
    @Autowired
    private MacroChinaPpiYearlyRepository macroChinaPpiYearlyRepository;
    @Autowired
    private MacroChinaPmiYearlyRepository macroChinaPmiYearlyRepository;
    @Autowired
    private MacroChinaCpiYearlyRepository macroChinaCpiYearlyRepository;
    @Autowired
    private StockATtmLyrRepository stockATtmLyrRepository;
    @Autowired
    private BondZhUsRateRepository bondZhUsRateRepository;
    @Autowired
    private MacroChinaSupplyOfMoneyRepository macroChinaSupplyOfMoneyRepository;
    @Autowired
    private AkShareManager akShareManager;

    private List<akShareUpdataIndicator> akShareUpdataIndicators;

    @PostConstruct
    private void init() {
        akShareUpdataIndicators = new ArrayList<>();

        akShareUpdataIndicators.add(new akShareUpdataIndicator("macro_china_pmi_yearly", MacroChinaPmiYearly.class, macroChinaPmiYearlyRepository));
        akShareUpdataIndicators.add(new akShareUpdataIndicator("macro_china_ppi_yearly", MacroChinaPpiYearly.class, macroChinaPpiYearlyRepository));
        akShareUpdataIndicators.add(new akShareUpdataIndicator("stock_a_ttm_lyr", StockATtmLyr.class, stockATtmLyrRepository));
        akShareUpdataIndicators.add(new akShareUpdataIndicator("macro_china_cpi_yearly", MacroChinaCpiYearly.class, macroChinaCpiYearlyRepository));
        akShareUpdataIndicators.add(new akShareUpdataIndicator("macro_china_supply_of_money", MacroChinaSupplyOfMoney.class, macroChinaSupplyOfMoneyRepository));
        akShareUpdataIndicators.add(new akShareUpdataIndicator("stock_a_all_pb", StockAAllPb.class, stockAAllPbRepository));
        akShareUpdataIndicators.add(new akShareUpdataIndicator("bond_zh_us_rate", BondZhUsRate.class, bondZhUsRateRepository));
    }

    /**
     * 更新所有指标
     */
    @Override
    public void updateIndicators() {
        for (int i = 0; i < akShareUpdataIndicators.size(); i++) {
            akShareUpdataIndicator updataIndicator = akShareUpdataIndicators.get(i);
            String path = updataIndicator.getPath();
            Class clazz = updataIndicator.getClazz();
            CrudRepository repository = updataIndicator.getRepository();
            //TODO      pmi ppi 接口数据有问题, 货币供应记录的不是公布时间
            if (path.equals("macro_china_pmi_yearly") || path.equals("macro_china_ppi_yearly")|| path.equals("macro_china_supply_of_money")){
                continue;
            }

            try {
                updateIndicator(path, clazz, repository);
            } catch (Exception e) {
                log.error(path + "保存失败", e);
            }
        }
    }

    @Override
    public Iterable findAKShare(String path, Map<String, String> param) {
        CrudRepository repository = findRepositoryByPath(path);
        if (repository == null){
            return null;
        }
        return repository.findAll();
    }

    private CrudRepository findRepositoryByPath(String path) {
        for (int i = 0; i < akShareUpdataIndicators.size(); i++) {
            akShareUpdataIndicator updataIndicator =  akShareUpdataIndicators.get(i);
            if (StringUtils.equals(updataIndicator.getPath(), path)){
                return updataIndicator.getRepository();
            }
        }
        return null;
    }

    /**
     * 更新指定指标
     */
    private <T> void updateIndicator(String path, Class<T> clazz, CrudRepository repository) throws Exception {
        //通过akshare获取数据
        String result = akShareManager.find(path, null);
        if (StringUtils.isBlank(result)) {
            return;
        }
        List<T> list;
        Class<?>[] interfaces = clazz.getInterfaces();
        try {
            if (ArrayUtils.contains(clazz.getInterfaces(), TimeIndicatorHandle.class)) {
                //结果转换
                JSONArray dataList = JSONArray.parseArray(result);
                list = new ArrayList<>(dataList.size());
                for (int j = 0; j < dataList.size(); j++) {
                    T data = clazz.newInstance();
                    data = (T) clazz.getDeclaredMethod("parsFromJson", String.class).invoke(data, dataList.getString(j));
                    list.add(data);
                }
            } else {
                list = (List<T>) clazz.getDeclaredMethod("parsListFromJson", String.class).invoke(null, result);
            }
        } catch (Exception e) {
            throw e;
        }
        //保存
        repository.saveAll(list);
    }
}

class akShareUpdataIndicator {
    //akshare路径
    private String path;
    //实体类
    private Class clazz;
    //存储
    private CrudRepository repository;

    public akShareUpdataIndicator(String path, Class clazz, CrudRepository repository) {
        this.path = path;
        this.clazz = clazz;
        this.repository = repository;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public CrudRepository getRepository() {
        return repository;
    }

    public void setRepository(CrudRepository repository) {
        this.repository = repository;
    }
}