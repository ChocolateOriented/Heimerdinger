import request from '@/utils/request'

// 查询持仓计划列表
export function listFinancePositionPlan(query) {
  return request({
    url: '/finance/financePositionPlan/list',
    method: 'get',
    params: query
  })
}

// 查询交易建议列表
export function tradeAdviceList(query) {
  return request({
    url: '/finance/financePositionPlan/tradeAdviceList',
    method: 'get',
    params: query
  })
}

// 查询持仓计划详细
export function getFinancePositionPlan(id) {
  return request({
    url: '/finance/financePositionPlan/' + id,
    method: 'get'
  })
}

// 新增持仓计划
export function addFinancePositionPlan(data) {
  return request({
    url: '/finance/financePositionPlan',
    method: 'post',
    data: data
  })
}

// 修改持仓计划
export function updateFinancePositionPlan(data) {
  return request({
    url: '/finance/financePositionPlan',
    method: 'put',
    data: data
  })
}

// 删除持仓计划
export function delFinancePositionPlan(id) {
  return request({
    url: '/finance/financePositionPlan/' + id,
    method: 'delete'
  })
}

// 导出持仓计划
export function exportFinancePositionPlan(query) {
  return request({
    url: '/finance/financePositionPlan/export',
    method: 'get',
    params: query
  })
}
