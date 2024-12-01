import request from '@/utils/request'

// 查询小说数据列表
export function listFictionData(query) {
  return request({
    url: '/gpt/fictionData/list',
    method: 'get',
    params: query
  })
}

// 查询小说数据详细
export function getFictionData(id) {
  return request({
    url: '/gpt/fictionData/' + id,
    method: 'get'
  })
}

// 新增小说数据
export function addFictionData(data) {
  return request({
    url: '/gpt/fictionData',
    method: 'post',
    data: data
  })
}

// 修改小说数据
export function updateFictionData(data) {
  return request({
    url: '/gpt/fictionData',
    method: 'put',
    data: data
  })
}

// 删除小说数据
export function delFictionData(id) {
  return request({
    url: '/gpt/fictionData/' + id,
    method: 'delete'
  })
}

// 导出小说数据
export function exportFictionData(query) {
  return request({
    url: '/gpt/fictionData/export',
    method: 'get',
    params: query
  })
}