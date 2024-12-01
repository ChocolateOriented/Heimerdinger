import request from '@/utils/request'

// 查询段落列表
export function listFictionParagraph(query) {
  return request({
    url: '/gpt/fictionParagraph/list',
    method: 'get',
    params: query
  })
}

// 查询段落详细
export function getFictionParagraph(id) {
  return request({
    url: '/gpt/fictionParagraph/' + id,
    method: 'get'
  })
}

// 新增段落
export function addFictionParagraph(data) {
  return request({
    url: '/gpt/fictionParagraph',
    method: 'post',
    data: data
  })
}

// 修改段落
export function updateFictionParagraph(data) {
  return request({
    url: '/gpt/fictionParagraph',
    method: 'put',
    data: data
  })
}

// 删除段落
export function delFictionParagraph(id) {
  return request({
    url: '/gpt/fictionParagraph/' + id,
    method: 'delete'
  })
}

// 导出段落
export function exportFictionParagraph(query) {
  return request({
    url: '/gpt/fictionParagraph/export',
    method: 'get',
    params: query
  })
}