import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/as/login',
    method: 'post',
    params: {username: data.username, password: data.password}
  })
}

export function phoneLogin(data) {
  return request({
    url: '/phone/phoneLogin',
    method: 'post',
    params: {phone: data.phone, code: data.code}
  })
}

export function emailLogin(data) {
  return request({
    url: '/email/emailLogin',
    method: 'post',
    params: {email: data.email, code: data.code}
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: {token}
  })
}

export function logout() {
  return request({
    url: '/as/logout',
    method: 'post'
  })
}
