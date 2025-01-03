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
          "中国国债收益率10年",
          "美国国债收益率10年",
          "中国官方制造业PMI",
          "中国PPI",
          "上证指数",
          "沪深300",
          "创业板",
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
            // { yField: "GDP", commonSeries: lineDateSeries },
            // { yField: "CPI", commonSeries: lineDateSeries }
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
              yName: "中国国债收益率10年",
              commonSeries: lineDateSeries,
              yAxis: {name: '收益率', type: 'value',max: 'dataMax', min: 'dataMin',},
            },
            {
              yName: "美国国债收益率10年",
              xFormat: formatDate(),
              commonSeries: lineDateSeries,
            }
          ],
          mergeKey: "date",
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
              yName: "中国官方制造业PMI",
              valueHandel: this.pmiHandel,
              // commonSeries: lineDateSeries,
              // yAxis: {name: 'pmi', type: 'value',max: 'dataMax', min: 'dataMin',},
            },
            {
              yName: "中国PPI",
              valueHandel: this.ppiHandel,
              commonSeries: lineDateSeries,
              yAxis: {name: 'ppi', type: 'value',max: 'dataMax', min: 'dataMin',},
            },
            {
              yName: "中国国债收益率10年",
              commonSeries: lineDateSeries,
              yAxis: {name: '收益率', type: 'value',max: 'dataMax', min: 'dataMin',},
            }
          ],
          dataHandleList:[
            { resultField:"pmi扩张", handel:function(tar){
                if(tar["中国官方制造业PMI"]){
                  return tar["中国官方制造业PMI"] -50;
                }
                return null;
              },
              dataDef:{
                yName: "pmi扩张",
                commonSeries: lineStackDateSeries,
              }
            },

          ],
          mergeKey: "date",
        },
        cnbs_watch:{
          dataDefineList: [
            {
              yName:"上证指数",
              series: {
                type: 'line',
                smooth: true,
                connectNulls: true,
                symbol: 'none',
                encode: {
                  x: "date",
                  y: "上证指数"
                },
              },
              yAxis: {name: '上证', type: 'value',offset: 120,},
            },
            {
              yName:"沪深300",
              xFormat: formatDate(),
              series: {
                type: 'line',
                smooth: true,
                connectNulls: true,
                symbol: 'none',
                encode: {
                  x: "date",
                  y: "沪深300"
                },
              },
              yAxis: {name: '沪深300', type: 'value',offset: 120,},
            },
            {
              yName:"创业板",
              series: {
                type: 'line',
                smooth: true,
                connectNulls: true,
                symbol: 'none',
                encode: {
                  x: "date",
                  y: "创业板"
                },
              },
              yAxis: {name: '创业板', type: 'value',offset: 120,},
            },
          ],
          dataHandleList:[
          ],
          mergeKey: "date",
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

