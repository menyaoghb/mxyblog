import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/mxy/login',
    method: 'post',
    params: { username:data.userName,password:data.password }
  })
}

export function getInfo() {
  return request({
    url: '/user/info',
    method: 'get',
    params: {}
  })
}

export function logout() {
  return request({
    url: '/mxy/logout',
    method: 'post'
  })
}
