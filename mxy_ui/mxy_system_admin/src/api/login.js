import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: '/system/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/system/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/system/logout',
    method: 'post'
  })
}
