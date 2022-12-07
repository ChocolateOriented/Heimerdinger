// 获取数据

import {parseTime} from "@/utils/ruoyi";

export function changeDate(dataList, filed, format) {
  for (let i = 0; i < dataList.length; i++) {
    let old = dataList[i];
    old[filed] = format(old[filed]);
  }
  return dataList;
}

//格式化时间
export function formatDate(newFmt,oldFmt){
  if (!newFmt){
    newFmt = '{y}-{m}-{d}';
  }
  return function (value){
      if (oldFmt){
        value = parseDate(value,oldFmt);
      }
      return  parseTime(value, newFmt);
  }
};

//降序
export function asc(p){
  return function(m,n){
    let a = m[p];
    let b = n[p];
    if (a < b) {
      return -1;
    }
    if (a > b) {
      return 1;
    }
    return 0;
  }
}

//合并数据
export function merge(mergeDataMap, dataList, keyField, keyFormat, valueField, mergeKey){

  //将Key格式化
  dataList = changeDate(dataList, keyField, keyFormat);

  for (let i = 0; i < dataList.length; i++) {
    let data = dataList[i];
    let keyValue = data[keyField];
    if (!mergeDataMap[keyValue]){
      mergeDataMap[keyValue] = {};
    }

    let mergeTarget = mergeDataMap[keyValue];
    mergeTarget[valueField] = data[valueField];
    mergeTarget[mergeKey] = keyValue;
  }
  return mergeDataMap;
}

//获取值数组
export function valueList(targetObject, sortFunction){
  let valueList = [];
  for (const targetObjectKey in targetObject) {
    valueList.push(targetObject[targetObjectKey])
  }
  valueList.sort(sortFunction);
  return valueList;
}

/**
 * 将字符串解析成日期
 * @param str 输入的日期字符串，如'2014-09-13'
 * @param fmt 字符串格式，默认'yyyy-MM-dd'，支持如下：y、M、d、H、m、s、S，不支持w和q
 * @returns 解析后的Date类型日期
 */
export function parseDate(str, fmt)
{
  fmt = fmt || 'yyyy-MM-dd';
  var obj = {y: 0, M: 1, d: 0, H: 0, h: 0, m: 0, s: 0, S: 0};
  fmt.replace(/([^yMdHmsS]*?)(([yMdHmsS])\3*)([^yMdHmsS]*?)/g, function(m, $1, $2, $3, $4, idx, old)
  {
    str = str.replace(new RegExp($1+'(\\d{'+$2.length+'})'+$4), function(_m, _$1)
    {
      obj[$3] = parseInt(_$1);
      return '';
    });
    return '';
  });
  obj.M--; // 月份是从0开始的，所以要减去1
  var date = new Date(obj.y, obj.M, obj.d, obj.H, obj.m, obj.s);
  if(obj.S !== 0) date.setMilliseconds(obj.S); // 如果设置了毫秒
  return date;
}
