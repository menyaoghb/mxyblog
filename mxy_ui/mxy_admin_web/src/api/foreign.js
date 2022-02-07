import request from '@/utils/request'

export function getRandWord(data) {
  return request({
    url: '/foreign/getRandWord',
    method: 'post',
    data
  })
}
