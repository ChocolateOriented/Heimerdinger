<template>
  <div class="app-container">
    <div style="height: 400px" id="money_watch"></div>
    <div style="height: 400px" id="cnbs_watch"></div>
    <div style="height: 400px" id="cycle_watch"></div>
    <div style="height: 400px" id="rate_watch"></div>
  </div>
</template>

<script>

import {parseTime} from "@/utils/ruoyi";
import {formatDate, getLastEL} from "@/utils/chart/DataUtils";
import {renderChartList, lineDateSeries, lineStackDateSeries} from "@/utils/chart/AKShareChartUtils";
import indicators from '@/utils/chart/data/indicators.json'
import {findLineFromMongo} from "@/api/finance/akShare";

export default {
  name: "macro_watch",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 持仓计划数据
      listFinancePositionPlan: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 50,
        traceId: null,
        name: null,
        realityAmount: null,
        targetAmount: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      lineReq: {
        startTime:'2011-01-20',
        endTime:'',
        columns:[
          '货币(狭义货币M1)同比增长',
          '货币和准货币（广义货币M2）同比增长',
          // '融资余额',
          'GDP',
          'CPI',
        ]
      },
      chartList: {
        money_watch: {
          dataDefineList: [
            {
              yName: "货币(狭义货币M1)同比增长",
              commonSeries: lineDateSeries,
              yAxis: {name: '百分比', type: 'value'}
            },
            {
              yName: "货币和准货币（广义货币M2）同比增长",
              commonSeries: lineDateSeries,
            },
            // {
            //   yField: "GDP",
            //   commonSeries: lineDateSeries,
            // },
            // {
            //   yField: "CPI",
            //   commonSeries: lineDateSeries,
            // }
          ],
          mergeKey: "date",
          dataHandleList:[
            { resultField:"货币M2流出M1", handel:function(tar){
                if(tar["货币和准货币（广义货币M2）同比增长"] && tar["货币(狭义货币M1)同比增长"]){
                  return tar["货币和准货币（广义货币M2）同比增长"] -  tar["货币(狭义货币M1)同比增长"] ;
                }
                return null;
              },
              dataDef:{
                yName: "货币M2流出M1",
                commonSeries: lineStackDateSeries,
                yAxis: {name: '货币M2流出M1', type: 'value',max: 'dataMax', min: 'dataMin',},
              }
            },
          ],
        },
        rate_watch:{
          dataDefineList: [
            {
              path: "bond_zh_us_rate",
              yName: "中国国债收益率10年",
              xFormat: formatDate(),
              commonSeries: lineDateSeries,
              yAxis: {name: '收益率', type: 'value',max: 'dataMax', min: 'dataMin',},
            },
            {
              path: "bond_zh_us_rate",
              xField: "日期",
              yName: "美国国债收益率10年",
              xFormat: formatDate(),
              commonSeries: lineDateSeries,
            },
            // {
            //   path: "currency_boc_safe",
            //   xField: "日期",
            //   yField: "美元",
            //   xFormat: formatDate(),
            //   commonSeries: lineDateSeries,
            //   yAxis: {name: '汇率', type: 'value',offset: 200,max: 'dataMax', min: 'dataMin',},
            // },
            // {
            //   path: "index_value_hist_funddb",
            //   param:{symbol:"万得全A", indicator:"市盈率"},
            //   xField: "日期",
            //   yField: "市盈率",
            //   xFormat: formatDate(),
            //   commonSeries: lineDateSeries,
            //   // yAxis: {name: '市盈率', type: 'value',max: 'dataMax', min: 'dataMin',},
            // },
          ],
          mergeKey: "日期",
          dataHandleList:[
            { resultField:"国债收益率资金流入导向", handel:function(tar){
                if(tar["中国国债收益率10年"] && tar["美国国债收益率10年"]){
                  // 短期可流动资金会因为利差流出，但是中国长期资金会因为中国降息增加收益率
                  return  tar["中国国债收益率10年"] - tar["美国国债收益率10年"] ;
                }
                return null;
              },
              dataDef:{
                yName: "国债收益率资金流入导向",
                series: {
                  type: 'line',
                  smooth: true,
                  connectNulls: true,
                  symbol: 'none',
                  encode: {
                    x: "日期",
                    y: "国债收益率资金流入导向"
                  },
                },
                yAxis: {name: '国债收益率资金流入导向', type: 'value',max: 'dataMax', min: 'dataMin',},
              }
            },
            // { resultField:"股债收益率比", handel:this.guZhaiLiCha,
            //   dataDef:{
            //     yName: "股债收益率比",
            //     series: {
            //       type: 'line',
            //       smooth: true,
            //       connectNulls: true,
            //       symbol: 'none',
            //       encode: {
            //         x: "日期",
            //         y: "股债收益率比"
            //       },
            //     },
            //     yAxis: {name: '百分比', type: 'value',max: 'dataMax', min: 'dataMin',},
            //   }
            // },
          ],
        },
        cycle_watch: {
          dataDefineList: [
            {
              path: "macro_china_pmi_yearly",
              xField: "date",
              yField: "pmi",
              valueHandel: this.pmiHandel,
              xFormat: formatDate(),
              // commonSeries: lineDateSeries,
              // yAxis: {name: 'pmi', type: 'value',max: 'dataMax', min: 'dataMin',},
            },
            {
              path: "macro_china_ppi_yearly",
              xField: "date",
              yField: "ppi",
              valueHandel: this.ppiHandel,
              xFormat: formatDate(),
              commonSeries: lineDateSeries,
              yAxis: {name: 'ppi', type: 'value',max: 'dataMax', min: 'dataMin',},
            },
            {
              data: indicators,
              xField: "quarter",
              yField: "opYoy",
              yName:"A股营业利润增长率",
              xFormat: formatDate(),
              // commonSeries: lineStackDateSeries,
              commonSeries: lineDateSeries,
              yAxis: {name: '百分比', type: 'value',max: 'dataMax', min: 'dataMin',},
            },
            {
              path: "bond_zh_us_rate",
              xField: "日期",
              yField: "中国国债收益率10年",
              xFormat: formatDate(),
              commonSeries: lineDateSeries,
              yAxis: {name: '收益率', type: 'value',max: 'dataMax', min: 'dataMin',},
            },
            {
              path: "bond_zh_us_rate",
              xField: "日期",
              yField: "美国国债收益率10年",
              xFormat: formatDate(),
              // commonSeries: lineDateSeries,
              // yAxis: {name: '收益率', type: 'value',max: 'dataMax', min: 'dataMin',},
            },
          ],
          dataHandleList:[
            { resultField:"pmi扩张", handel:function(tar){
                if(tar["pmi"]){
                  return tar["pmi"] -50;
                }
                return null;
              },
              dataDef:{
                yName: "pmi扩张",
                commonSeries: lineStackDateSeries,
              }
            },

          ],
          mergeKey: "日期",
        },
        cnbs_watch:{
          dataDefineList: [
            {
              path: "stock_zh_index_daily",
              param:{symbol:"sh000001"},
              xField: "date",
              yField: "close",
              yName:"上证指数",
              xFormat: formatDate(),
              series: {
                type: 'line',
                smooth: true,
                connectNulls: true,
                symbol: 'none',
                encode: {
                  x: "日期",
                  y: "上证指数"
                },
              },
              yAxis: {name: '上证', type: 'value',offset: 120,},
            },
            {
              path: "stock_zh_index_daily",
              param:{symbol:"sh000300"},
              xField: "date",
              yField: "close",
              yName:"沪深300",
              xFormat: formatDate(),
              series: {
                type: 'line',
                smooth: true,
                connectNulls: true,
                symbol: 'none',
                encode: {
                  x: "日期",
                  y: "沪深300"
                },
              },
              yAxis: {name: '沪深300', type: 'value',offset: 120,},
            },
            {
              path: "stock_zh_index_daily",
              param:{symbol:"sz399006"},
              xField: "date",
              yField: "close",
              yName:"创业板",
              xFormat: formatDate(),
              series: {
                type: 'line',
                smooth: true,
                connectNulls: true,
                symbol: 'none',
                encode: {
                  x: "日期",
                  y: "创业板"
                },
              },
              yAxis: {name: '创业板', type: 'value',offset: 120,},
            },
          ],
          dataHandleList:[
          ],
          mergeKey: "日期",
        }
      },
      mergeData:[]
    };
  },
  created() {
    this.getData();
  },
  mounted() {
    //渲染图表
    findLineFromMongo(this.lineReq).then(response => {
      this.mergeData = response.data;
      renderChartList(this.chartList, this.mergeData);
    })
  },
  methods: {
    getData() {
    },
    pmiHandel(dataList){
      let resultList = [];
      let date = new Date("2005-02-01");

      for (let i = 0; i < dataList.length; i++) {
        date = new Date( date.setMonth(date.getMonth() + 1));
        if (dataList[i] === 0){
          dataList[i] = dataList[i-1];
        }
        resultList.push({"pmi":dataList[i],"date":parseTime(date)});
      }
      console.info("pmiHandel")
      console.info(resultList)
      return resultList;
    },
    ppiHandel(dataList){
      let resultList = [];
      let date = new Date("1995-07-01");

      for (let i = 0; i < dataList.length; i++) {
        date = new Date( date.setMonth(date.getMonth() + 1));
        if (dataList[i] === 0){
          dataList[i] = dataList[i-1];
        }
        resultList.push({"ppi":dataList[i],"date":parseTime(date)});
      }
      return resultList;
    },
    usappiHandel(dataList){
      let resultList = [];
      let date = new Date("2008-02-26");

      for (let i = 0; i < dataList.length; i++) {
        date = new Date( date.setMonth(date.getMonth() + 1));
        if (dataList[i] === 0){
          dataList[i] = dataList[i-1];
        }
        resultList.push({"美国ppi":dataList[i],"date":parseTime(date)});
      }
      return resultList;
    },

    addEvent(chartDef, mergeData){
      let eventList=[
        {日期: "2007年8月9日", 事件:"次贷危机"},
        {日期: "2014年9月30日", 事件:"开启融券融资"},
        {日期: "2018年3月22日", 事件:"中美贸易战"},
        {日期: "2020年1月30日", 事件:"新冠疫情"},
      ];
      let dataDefineList = chartDef.dataDefineList;
      // dataDefineList.push({});
    },
    guZhaiLiCha(tar){
      if (tar["市盈率"] && tar["中国国债收益率10年"]){
        return (100/tar["市盈率"])/tar["中国国债收益率10年"].toFixed(2);
      }
      return null;
    },
    // 获取平均GDP
    averageGDP(tar, index, mergeData, gdp = "GDP") {

      if (!tar[gdp]){
        return tar["gdpAVG"];
      }
      let GDP = tar["GDP"];
      let lastIndex = index - 1;
      if (lastIndex > 0){
        mergeData[lastIndex]["gdpAVG"] = GDP;
      }
      let nextIndex = index + 1;
      if (nextIndex < mergeData.length){
        mergeData[nextIndex]["gdpAVG"] = GDP;
      }
      return GDP;
    },
    superCurrency(tar){
      if (tar["gdpAVG"] && tar["CPI"] && tar["货币和准货币（广义货币M2）同比增长"]){
        return  tar["货币和准货币（广义货币M2）同比增长"] - tar["gdpAVG"] -tar["CPI"];
      }
      return null
    }

  }
};
</script>

