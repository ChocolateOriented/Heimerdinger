<template>
  <div class="app-container">
    <div style="height: 800px" id="money_watch"></div>
    <div style="height: 400px" id="cycle_watch"></div>
  </div>
</template>

<script>

import {parseTime} from "@/utils/ruoyi";
import {formatDate} from "@/utils/chart/DataUtils";
import {renderChartList, lineDateSeries} from "@/utils/chart/AKShareChartUtils";

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
      chartList: {
        money_watch: {
          dataDefineList: [
            {
              path: "macro_china_supply_of_money",
              xField: "统计时间",
              yField: "货币和准货币（广义货币M2）同比增长",
              xFormat: formatDate(),
              commonSeries: lineDateSeries,
              yAxis: {name: '百分比', type: 'value'}
            },
            {
              path: "macro_china_supply_of_money",
              xField: "统计时间",
              yField: "货币(狭义货币M1)同比增长",
              xFormat: formatDate(),
              commonSeries: lineDateSeries,
            },
            // {
            //   path: "stock_margin_sse",
            //   param:{start_date:"2010401",end_date:parseTime(new Date(),'{y}{m}{d}')},
            //   xField: "信用交易日期",
            //   yField: "融资余额",
            //   xFormat: formatDate('{y}-{m}-{d}','yyyyMMdd'),
            //   yAxis: {name: '融资余额', type: 'value',},
            // },
            {
              path: "stock_zh_index_daily",
              param:{symbol:"sh000001"},
              xField: "date",
              yField: "close",
              xFormat: formatDate(),
              commonSeries: lineDateSeries,
              yAxis: {name: '上证', type: 'value',offset: 120,},
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
            //   path: "macro_china_ppi_yearly",
            //   xField: "date",
            //   yField: "ppi",
            //   valueHandel: this.ppiHandel,
            //   xFormat: formatDate(),
            //   commonSeries: lineDateSeries,
            //   yAxis: {name: 'ppi', type: 'value',offset: 80,max: 'dataMax', min: 'dataMin',},
            // },
          ],
          mergeKey: "日期",
        },
        cycle_watch: {
          dataDefineList: [
            // {
            //   path: "bond_zh_us_rate",
            //   xField: "日期",
            //   yField: "中国国债收益率10年",
            //   xFormat: formatDate(),
            // },
            // {
            //   path: "bond_zh_us_rate",
            //   xField: "日期",
            //   yField: "美国国债收益率10年",
            // },
            {
              path: "index_value_hist_funddb",
              param:{symbol:"万得全A", indicator:"市盈率"},
              xField: "日期",
              yField: "市盈率",
              xFormat: formatDate(),
              commonSeries: lineDateSeries,
              yAxis: {name: '市盈率', type: 'value',max: 'dataMax', min: 'dataMin',},
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

          ],
          dataHandleList:[
            this.guZhaiLiCha,
          ],
          mergeKey: "日期",
        },
      },
    };
  },
  created() {
    this.getData();
  },
  mounted() {
    //渲染图表
    renderChartList(this.chartList);
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
      console.info(resultList)
      return resultList;
    },
    ppiHandel(dataList){
      let resultList = [];
      let date = new Date("1995-08-01");

      for (let i = 0; i < dataList.length; i++) {
        date = new Date( date.setMonth(date.getMonth() + 1));
        if (dataList[i] === 0){
          dataList[i] = dataList[i-1];
        }
        resultList.push({"ppi":dataList[i],"date":parseTime(date)});
      }
      return resultList;
    },
    addEvent(chartDef, mergeData){
      let eventList=[
        {日期: "2007年8月9日", 事件:"次贷危机"},
        {日期: "2018年3月22日", 事件:"中美贸易战"},
        {日期: "2020年1月30日", 事件:"新冠疫情"},
      ];
      let dataDefineList = chartDef.dataDefineList;
      // dataDefineList.push({});
    },
    guZhaiLiCha(chartDef, mergeData){

    },

  }
};
</script>

