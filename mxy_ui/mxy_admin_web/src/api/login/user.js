import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/as/login',
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
    url: '/as/logout',
    method: 'post'
  })
}
