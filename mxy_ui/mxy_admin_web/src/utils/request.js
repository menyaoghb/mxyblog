import axios from 'axios'
import {MessageBox, Message} from 'element-ui'
import store from '@/store'
import {getToken,setToken} from '@/utils/auth'

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
    // do something before request is sent

    var arr, reg = new RegExp("(^| )" + "Authorization" + "=([^;]*)(;|$)")
    if (arr = document.cookie.match(reg)) {
      if (unescape(arr[2]) != undefined)
        setToken(unescape(arr[2]))
        config.headers['Authorization'] = unescape(arr[2])
    }

    if (store.getters.token) {
      // let each request carry token
      // ['Authorization'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['Authorization'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    // 如果自定义代码不是200，则判断为错误。
    if (res.code != 200) {
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      })

      // 50008:非法令牌;50012:其他客户端登录;50014:令牌过期;
      if (res.code == 401) {
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
      }
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
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
