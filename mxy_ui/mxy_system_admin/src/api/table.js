import request from '@/utils/request'

export function getList(params) {

  return request({
    url: '/system/sysUser/getList',
    method: 'post',
    params:{
      'currentPage':1,
      'pageSize':2
    }
  })
}
