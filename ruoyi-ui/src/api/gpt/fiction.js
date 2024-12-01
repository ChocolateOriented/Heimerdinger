import request from '@/utils/request'

// 查询小说列表
export function listFiction(query) {
  return request({
    url: '/gpt/fiction/list',
    method: 'get',
    params: query
  })
}

// 查询小说详细
export function getFiction(id) {
  return request({
    url: '/gpt/fiction/' + id,
    method: 'get'
  })
}

// 新增小说
export function addFiction(data) {
  return request({
    url: '/gpt/fiction',
    method: 'post',
    data: data
  })
}

// 修改小说
export function updateFiction(data) {
  return request({
    url: '/gpt/fiction',
    method: 'put',
    data: data
  })
}

// 删除小说
export function delFiction(id) {
  return request({
    url: '/gpt/fiction/' + id,
    method: 'delete'
  })
}

// 导出小说
export function exportFiction(query) {
  return request({
    url: '/gpt/fiction/export',
    method: 'get',
    params: query
  })
}