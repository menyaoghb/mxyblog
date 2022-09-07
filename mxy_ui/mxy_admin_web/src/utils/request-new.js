import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import store from '@/store'
import {getToken} from '@/utils/auth'

// 创建一个axios实例
const service = axios.create({
  baseURL: '/api', // url = base url + request url
  withCredentials: true, // 当跨域请求时发送cookie
  headers: {
    token: getToken()
  },
  timeout: 60000 // 请求超时
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      config.headers['Authorization'] = getToken()
    }
    return config
  },
  error => {
    console.log(error) // for debug
    return Promise.reject(error)
  }
)
/*直接返回结果，不做拦截*/
// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === 401) {
      // 重新登录
      MessageBox.confirm('您已退出登录，您可以取消以留在此页面，或重新登录', '确认注销', {
        confirmButtonText: '重新登入',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('user/resetToken').then(() => {
          location.reload()
        })
      })
      return Promise.reject(new Error(res.message || 'Error'))
    }
    return response.data
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: "登录失效",
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject("请重新登录")
  }
)

export default service
