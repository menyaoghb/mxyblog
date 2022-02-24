import request from '@/utils/request'

export function sysInfoDetail(data) {
  return request({
    url: '/sysInfo/detail',
    method: 'post',
    data
  })
}
