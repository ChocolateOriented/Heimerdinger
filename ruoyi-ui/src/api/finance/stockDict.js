import request from '@/utils/request'

// 查询股票字典列表
export function listStockDict(query) {
  return request({
    url: '/finance/stockDict/list',
    method: 'get',
    params: query
  })
}

// 查询股票字典详细
export function getStockDict(id) {
  return request({
    url: '/finance/stockDict/' + id,
    method: 'get'
  })
}

// 新增股票字典
export function addStockDict(data) {
  return request({
    url: '/finance/stockDict',
    method: 'post',
    data: data
  })
}

// 修改股票字典
export function updateStockDict(data) {
  return request({
    url: '/finance/stockDict',
    method: 'put',
    data: data
  })
}

// 删除股票字典
export function delStockDict(id) {
  return request({
    url: '/finance/stockDict/' + id,
    method: 'delete'
  })
}

// 导出股票字典
export function exportStockDict(query) {
  return request({
    url: '/finance/stockDict/export',
    method: 'get',
    params: query
  })
}