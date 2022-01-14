import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/mxy/login',
    method: 'post',
    params: { username:data.username,password:data.password }
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/mxy/logout',
    method: 'post'
  })
}
