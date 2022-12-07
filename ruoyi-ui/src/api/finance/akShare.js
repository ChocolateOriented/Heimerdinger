import request from '@/utils/request'

// 查询股票追踪详细
export function find(query) {
  return request({
    url: '/ak_share/find',
    method: 'get',
    params: query
  })
}
