import request from '@/utils/request'

// 查询追踪日志列表
export function listTraceLog(query) {
  return request({
    url: '/finance/traceLog/list',
    method: 'get',
    params: query
  })
}

// 查询追踪日志详细
export function getTraceLog(id) {
  return request({
    url: '/finance/traceLog/' + id,
    method: 'get'
  })
}

// 新增追踪日志
export function addTraceLog(data) {
  return request({
    url: '/finance/traceLog',
    method: 'post',
    data: data
  })
}

// 修改追踪日志
export function updateTraceLog(data) {
  return request({
    url: '/finance/traceLog',
    method: 'put',
    data: data
  })
}

// 删除追踪日志
export function delTraceLog(id) {
  return request({
    url: '/finance/traceLog/' + id,
    method: 'delete'
  })
}

// 导出追踪日志
export function exportTraceLog(query) {
  return request({
    url: '/finance/traceLog/export',
    method: 'get',
    params: query
  })
}