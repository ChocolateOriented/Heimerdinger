// 获取数据

import * as echarts from "echarts";
import {find, findLineFromMongo} from "@/api/finance/akShare";
import {asc, merge, valueList} from "@/utils/chart/DataUtils";
import da from "element-ui/src/locale/lang/da";

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
export function renderChartList(chartList, mergeData) {

  for (const chartKey in chartList) {
    let chartDef = chartList[chartKey];
    chartDef.option = defaultOption();
    //处理数据
    let dataHandleList = chartDef.dataHandleList;
    if (dataHandleList){
      for (let i = 0; i < dataHandleList.length; i++) {
        let handleDef = dataHandleList[i];
        doHandel(handleDef, chartDef, mergeData);
      }
    }
    //渲染图表
    renderChart(chartKey, chartDef, mergeData);
  }
}

async function getData(chartDef) {

  let dataDefineList = chartDef.dataDefineList;
  let mergeDataMap = {}
  let mergeData = {};

  let mergeKey = chartDef.mergeKey;

  for (let i = 0; i < dataDefineList.length; i++) {
    let chartDataDefine = dataDefineList[i];
    let targetData = null;
    if (chartDataDefine.path){
      await find(chartDataDefine).then(response => {
        if (response.code == 200 && response.data) {
          targetData = response.data;
          if (chartDataDefine.valueHandel) {
            targetData = chartDataDefine.valueHandel(response.data);
          }

        } else {
          console.error(response);
        }
      });
    }
    if (chartDataDefine.data){
      targetData = chartDataDefine.data;
    }
    mergeDataMap = merge(mergeDataMap, targetData, chartDataDefine, mergeKey);
    mergeData = valueList(mergeDataMap, asc(mergeKey))
  }
  return mergeData;
}

//数据处理
function doHandel(handelDef, chartDef, mergeData){
  let resultField = handelDef.resultField;
  let handel = handelDef.handel;
  let dataDef = handelDef.dataDef;

  for (let i = 0; i < mergeData.length; i++) {
    let tar = mergeData[i];
    tar[resultField] = handel(tar, i, mergeData)
  }

  if (! dataDef){
    dataDef = {
      yName: resultField,
      commonSeries: lineDateSeries,
      yAxis: {name: resultField, type: 'value',max: 'dataMax', min: 'dataMin',},
    }
  }

  chartDef.dataDefineList.push(dataDef);
};

/**
 * 渲染图表
 * @param chartKey
 * @param chartDef
 * @param mergeData
 */
function renderChart(chartKey, chartDef, mergeData) {
  console.info(mergeData);
  let dataDefineList = chartDef.dataDefineList;
  let mergeKey = chartDef.mergeKey;
  let option = chartDef.option;
  option.dataset.dimensions.push(mergeKey);

  for (let i = 0; i < dataDefineList.length; i++) {
    let chartDataDefine = dataDefineList[i];
    // 获取折线定义
    if (chartDataDefine.commonSeries){
      chartDataDefine.series = chartDataDefine.commonSeries(mergeKey,chartDataDefine.yName);
    }
    //增加图形
    if (chartDataDefine.series) {
      //增加维度
      option.dataset.dimensions.push(chartDataDefine.yName);
      chartDataDefine.series.name = chartDataDefine.yName;
      //增加Y轴
      if (chartDataDefine.yAxis) {
        option.yAxis.push(chartDataDefine.yAxis);
        chartDataDefine.series.yAxisIndex = option.yAxis.length - 1;
      }
      if (mergeData.length > 3000){
        chartDataDefine.series.symbol = "none";
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

export function lineDateSeries (x, y) {
  return {
    type: 'line',
    smooth: true,
    connectNulls: true,
    encode: {
      x: x,
      y: y
    },
  };
}


export function lineStackDateSeries (x, y) {
  let series = lineDateSeries(x,y)
  series.stack = "Total";
  series.emphasis= {
    focus: 'series'
  };
  series.stackStrategy= 'all';
  series.areaStyle={};
  return series;
}

function defaultOption() {
  return {
    legend: {
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
        startValue: "2002-12-01",
      },
      {
        show: true,
        type: 'slider',
        top: '90%',
        // start: 50,
        end: 100,
        startValue: "2002-12-01",
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
      name: "date",
      axisLine: {onZero: false},
      splitLine: {show: false},
    },
    yAxis: [],
    series: [
    ],
  }
};


