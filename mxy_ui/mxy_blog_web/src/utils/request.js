import axios from 'axios'
import store from '@/store'

// create an axios instance
const service = axios.create({
    baseURL: '/system',
    timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
    config => {
        // do something before request is sent

        // if (store.getters.token) {
        //     config.headers['X-Token'] = getToken()
        // }
        return config
    },
    error => {
        // do something with request error
        console.log(error) // for debug
        return Promise.reject(error)
    }
)

// response interceptor
service.interceptors.response.use(
    /**
     * 如果您想获得http信息，如头信息或状态
     * 请将  response => response
     */

    /**
     * 通过自定义代码确定请求状态
     * 这里只是一个例子
     * 您也可以通过HTTP状态码来判断状态
     */
    response => {
        const res = response.data
        // store.commit('SET_LOADING',false);

        // 如果定制代码不是200，则判定为错误。
        if (res.code !== 200) {
            return Promise.reject(new Error(res.message || 'Error'))
        } else {
            return res
        }
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)

export default service
