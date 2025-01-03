import request from '@/utils/request'

export function listStockSubject(query) {
  return request({
    url: '/finance/stockSubject/list',
    method: 'get',
    params: query
  })
}

// 查询股票字典列表
export function listStockReport(query) {
  return request({
    url: '/finance/stockSubject/reportList',
    method: 'get',
    params: query
  })
}
