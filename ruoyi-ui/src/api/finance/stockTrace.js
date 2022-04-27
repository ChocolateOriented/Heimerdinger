import request from '@/utils/request'

// 查询股票追踪列表
export function listStockTrace(query) {
  return request({
    url: '/finance/stockTrace/list',
    method: 'get',
    params: query
  })
}

// 查询股票追踪详细
export function getStockTrace(id) {
  return request({
    url: '/finance/stockTrace/' + id,
    method: 'get'
  })
}

// 查询股票当前
export function findCurrentInfo(query) {
  return request({
    url: '/finance/stockTrace/findCurrentInfo',
    method: 'get',
    params: query
  })
}

// 新增股票追踪
export function addStockTrace(data) {
  return request({
    url: '/finance/stockTrace',
    method: 'post',
    data: data
  })
}

// 修改股票追踪
export function updateStockTrace(data) {
  return request({
    url: '/finance/stockTrace',
    method: 'put',
    data: data
  })
}

// 删除股票追踪
export function delStockTrace(id) {
  return request({
    url: '/finance/stockTrace/' + id,
    method: 'delete'
  })
}

// 导出股票追踪
export function exportStockTrace(query) {
  return request({
    url: '/finance/stockTrace/export',
    method: 'get',
    params: query
  })
}
