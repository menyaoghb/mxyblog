import request from '@/utils/request'

export function getRandWord(data) {
  return request({
    url: '/foreign/getRandWord',
    method: 'post',
    data
  })
}

export function sendMessageCode(phoneNo) {
  return request({
    url: '/foreign/sms/customer/sendMessageCode',
    method: 'get',
    params: {phoneNo: phoneNo}
  })
}
