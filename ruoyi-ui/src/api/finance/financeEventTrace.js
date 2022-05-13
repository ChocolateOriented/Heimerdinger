import request from '@/utils/request'

// 查询经济事件追踪列表
export function listFinanceEventTrace(query) {
  return request({
    url: '/finance/financeEventTrace/list',
    method: 'get',
    params: query
  })
}

// 查询经济事件追踪详细
export function getFinanceEventTrace(id) {
  return request({
    url: '/finance/financeEventTrace/' + id,
    method: 'get'
  })
}

// 新增经济事件追踪
export function addFinanceEventTrace(data) {
  return request({
    url: '/finance/financeEventTrace',
    method: 'post',
    data: data
  })
}

// 修改经济事件追踪
export function updateFinanceEventTrace(data) {
  return request({
    url: '/finance/financeEventTrace',
    method: 'put',
    data: data
  })
}

// 删除经济事件追踪
export function delFinanceEventTrace(id) {
  return request({
    url: '/finance/financeEventTrace/' + id,
    method: 'delete'
  })
}

// 导出经济事件追踪
export function exportFinanceEventTrace(query) {
  return request({
    url: '/finance/financeEventTrace/export',
    method: 'get',
    params: query
  })
}