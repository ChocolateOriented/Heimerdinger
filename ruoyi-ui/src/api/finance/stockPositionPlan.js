import request from '@/utils/request'

// 查询股票持仓计划列表
export function listStockPositionPlan(query) {
  return request({
    url: '/finance/stockPositionPlan/list',
    method: 'get',
    params: query
  })
}

// 查询股票持仓计划详细
export function getStockPositionPlan(id) {
  return request({
    url: '/finance/stockPositionPlan/' + id,
    method: 'get'
  })
}

// 新增股票持仓计划
export function addStockPositionPlan(data) {
  return request({
    url: '/finance/stockPositionPlan',
    method: 'post',
    data: data
  })
}

// 修改股票持仓计划
export function updateStockPositionPlan(data) {
  return request({
    url: '/finance/stockPositionPlan',
    method: 'put',
    data: data
  })
}

// 批量保存股票持仓计划
export function saveStockPositionPlanList(data) {
  return request({
    url: '/finance/stockPositionPlan/saveStockPositionPlanList',
    method: 'post',
    data: data
  })
}

// 删除股票持仓计划
export function delStockPositionPlan(id) {
  return request({
    url: '/finance/stockPositionPlan/' + id,
    method: 'delete'
  })
}

// 导出股票持仓计划
export function exportStockPositionPlan(query) {
  return request({
    url: '/finance/stockPositionPlan/export',
    method: 'get',
    params: query
  })
}
