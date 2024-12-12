import request from '@/utils/request'

// 查询股票追踪详细
export function find(query) {
  return request({
    url: '/ak_share/find',
    method: 'get',
    params: query
  })
}

  export function findLineFromMongo(data) {
    return request({
      url: '/ak_share/findLineFromMongo',
      method: 'post',
      data: data
    })
  }


