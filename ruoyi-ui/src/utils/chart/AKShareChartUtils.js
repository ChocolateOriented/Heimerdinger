// 获取数据

import * as echarts from "echarts";
import {find} from "@/api/finance/akShare";
import {asc, merge, valueList} from "@/utils/chart/DataUtils";

/**
 * 渲染图表
 * @param chartList
 *       chartList: {
 *         money_watch: {
 *           dataDefineList: [
 *             {
 *              //akSHare 路径
 *               path: "macro_china_ppi_yearly",
 *               //横坐标值
 *               xField: "date",
 *               //纵坐标值
 *               yField: "ppi",
 *               //值处理
 *               valueHandel: this.ppiHandel,
 *               xFormat: formatDate(),
 *               //使用独立Y轴
 *               yAxis: {name: 'ppi', type: 'value',offset: 80,max: 'dataMax', min: 'dataMin',},
 *               //以折线图展示
 *               series: {type: 'line', smooth: true,connectNulls:true,}
 *             },
 *           ],
 *           mergeKey: "日期",
 *         },
*         },
 */
export function renderChartList(chartList) {

  for (const chartKey in chartList) {
    let chartDef = chartList[chartKey];
    chartDef.option = defaultOption();
    //获取数据
    getData(chartDef).then(function (mergeData) {

      //处理数据
      let dataHandleList = chartDef.dataHandleList;
      if (dataHandleList){
        for (let i = 0; i < dataHandleList.length; i++) {
          dataHandleList[i](chartDef, mergeData);
        }
      }
      //渲染图表
      renderChart(chartKey, chartDef, mergeData);
    });
  }
}

async function getData(chartDef) {

  let dataDefineList = chartDef.dataDefineList;
  let mergeDataMap = getDateMap();
  let mergeData = {};

  let mergeKey = chartDef.mergeKey;

  for (let i = 0; i < dataDefineList.length; i++) {
    let chartDataDefine = dataDefineList[i];

    await find(chartDataDefine).then(response => {
      if (response.code == 200 && response.data) {
        if (chartDataDefine.valueHandel) {
          response.data = chartDataDefine.valueHandel(response.data);
        }
        mergeDataMap = merge(mergeDataMap, response.data, chartDataDefine.xField, chartDataDefine.xFormat, chartDataDefine.yField, mergeKey);
        mergeData = valueList(mergeDataMap, asc(mergeKey));
      } else {
        console.error(response);
      }
    });
  }
  return mergeData;
}

function renderChart(chartKey, chartDef, mergeData) {
  console.info(mergeData);
  let dataDefineList = chartDef.dataDefineList;
  let mergeKey = chartDef.mergeKey;
  let option = chartDef.option;
  option.dataset.dimensions.push(mergeKey);

  for (let i = 0; i < dataDefineList.length; i++) {
    let chartDataDefine = dataDefineList[i];

    if (chartDataDefine.commonSeries){
      chartDataDefine.series = chartDataDefine.commonSeries(mergeKey,chartDataDefine.yField);
    }
    //增加图形
    if (chartDataDefine.series) {
      //增加维度
      option.dataset.dimensions.push(chartDataDefine.yField);
      option.legend.data.push(chartDataDefine.yField);
      chartDataDefine.series.name = chartDataDefine.yField;
      //增加Y轴
      if (chartDataDefine.yAxis) {
        option.yAxis.push(chartDataDefine.yAxis);
        chartDataDefine.series.yAxisIndex = option.yAxis.length - 1;
      }
      option.series.push(chartDataDefine.series);
    }
  }

  console.info(chartKey);
  console.info(option);

  //初始化EChartDom
  let chartDom = document.getElementById(chartKey);
  let myChart = echarts.init(chartDom);
  option.dataset.source = mergeData;
  option && myChart.setOption(option);
}

//获取日期Map
function getDateMap(){
  let start = new Date("1990-01-01");
  let end = new Date();
  let mergeDataMap = {};
  while (start.getTime() < end){
    mergeDataMap[start]={};
    start = new Date( start.setDate(start.getDate() + 1));
  }
  return mergeDataMap;
}

export function lineDateSeries (x, y) {
  return {
    type: 'line',
    smooth: true,
    connectNulls: true,
    symbol: 'none',
    encode: {
      x: x,
      y: y
    },
  };
}

function defaultOption() {
  return {
    legend: {
      // orient: 'vertical',
      // right: 10,
      // top: 'center',
      data:[],
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross'
      }
    },
    dataZoom: [
      {
        type: 'inside',
        end: 100,
        startValue: "2005-01-01",
      },
      {
        show: true,
        type: 'slider',
        top: '90%',
        // start: 50,
        end: 100,
        startValue: "2005-01-01",
      }
    ],
    dataset: {
      // 用 dimensions 指定了维度的顺序。直角坐标系中，如果 X 轴 type 为 category，
      // 默认把第一个维度映射到 X 轴上，后面维度映射到 Y 轴上。
      // 如果不指定 dimensions，也可以通过指定 series.encode
      // 完成映射，参见后文。
      dimensions: [],
      source: {},
    },
    xAxis: {
      type: 'time',
      name: "日期",
      axisLine: {onZero: false},
      splitLine: {show: false},
    },
    yAxis: [],
    series: [
    ],
  }
};
