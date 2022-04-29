import request from '@/utils/request'

// 查询股票数据映射配置列表
export function listStockDataConfig(query) {
  return request({
    url: '/finance/stockDataConfig/list',
    method: 'get',
    params: query
  })
}

// 查询股票数据映射配置详细
export function getStockDataConfig(id) {
  return request({
    url: '/finance/stockDataConfig/' + id,
    method: 'get'
  })
}

// 新增股票数据映射配置
export function addStockDataConfig(data) {
  return request({
    url: '/finance/stockDataConfig',
    method: 'post',
    data: data
  })
}

// 修改股票数据映射配置
export function updateStockDataConfig(data) {
  return request({
    url: '/finance/stockDataConfig',
    method: 'put',
    data: data
  })
}

// 删除股票数据映射配置
export function delStockDataConfig(id) {
  return request({
    url: '/finance/stockDataConfig/' + id,
    method: 'delete'
  })
}

// 导出股票数据映射配置
export function exportStockDataConfig(query) {
  return request({
    url: '/finance/stockDataConfig/export',
    method: 'get',
    params: query
  })
}