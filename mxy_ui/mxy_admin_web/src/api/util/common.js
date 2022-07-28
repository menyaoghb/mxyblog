import axios from 'axios'
import {getToken} from "@/utils/auth";

const http = axios.create({
  headers: {
    token: getToken()
  },
  responseType: 'blob'
})

http.interceptors.request.use(
  config => {
    if (config.method === 'get' || config.method === 'GET') {
      config.url = encodeURI(config.url)
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// GET下载文件
export function downloadForGet(url, fileName) {
  http.get(url).then(res => {
    if (res.status === 200) {
      if (window.navigator.msSaveOrOpenBlob) {
        // 兼容ie11
        try {
          const blob = new Blob([res.data])
          window.navigator.msSaveOrOpenBlob(blob, fileName)
        } catch (e) {
          console.log(e)
        }
      } else {
        const blob = res.data
        const reader = new FileReader()
        reader.readAsDataURL(blob) // 转换为base64，可以直接放入a标签href
        reader.onload = function (e) {
          console.log(e) // 查看有没有接收到数据流
          // 转换完成，创建一个a标签用于下载
          const a = document.createElement('a')
          a.download = fileName // 自定义下载文件名称
          a.href = e.target.result
          document.body.appendChild(a)
          a.click()
          a.remove()
        }
      }
    }
  })
}

// POST下载文件
export function downloadForPost(data, url, fileName) {
  axios({
    url: url,
    method: 'post',
    responseType: 'blob',
    headers: {
      token: getToken()
    },
    data: data
  }).then(res => {
    if (res.status === 200) {
      if (window.navigator.msSaveOrOpenBlob) {
        // 兼容ie11
        try {
          const blob = new Blob([res.data])
          window.navigator.msSaveOrOpenBlob(blob, fileName)
        } catch (e) {
          console.log(e)
        }
      } else {
        const blob = res.data
        const reader = new FileReader()
        reader.readAsDataURL(blob) // 转换为base64，可以直接放入a标签href
        reader.onload = function (e) {
          console.log(e) // 查看有没有接收到数据流
          // 转换完成，创建一个a标签用于下载
          const a = document.createElement('a')
          a.download = fileName // 自定义下载文件名称
          a.href = e.target.result
          document.body.appendChild(a)
          a.click()
          a.remove()
        }
      }
    }
  }).catch(err => {
    console.log(err.message)
  })
}

// 获取当前时间
export function getTimeNow(time) {
  let Time = ''
  if (time) {
    Time = new Date(time)
  } else {
    Time = new Date()
  }
  let month = Time.getMonth() + 1
  let day = Time.getDate()
  let hours = Time.getHours()
  let minutes = Time.getMinutes()
  let seconds = Time.getSeconds()
  if (month.toString().length === 1) month = '0' + month.toString()
  if (day.toString().length === 1) day = '0' + day.toString()
  if (hours.toString().length === 1) hours = '0' + hours.toString()
  if (minutes.toString().length === 1) minutes = '0' + minutes.toString()
  if (seconds.toString().length === 1) seconds = '0' + seconds.toString()
  return `${month.toString()}-${day.toString()} ${hours.toString()}:${minutes.toString()}:${seconds.toString()}`
}

// 格式化请求
export function stringify(data) {
  const formData = new FormData()
  for (const key in data) {
    // eslint-disable-next-line no-prototype-builtins
    if (data.hasOwnProperty(key)) {
      if (data[key]) {
        if (data[key].constructor === Array) {
          if (data[key][0]) {
            if (data[key][0].constructor === Object) {
              formData.append(key, JSON.stringify(data[key]))
            } else {
              data[key].forEach((item, index) => {
                formData.append(key + `[]`, item)
              })
            }
          } else {
            formData.append(key + '[]', '')
          }
        } else if (data[key].constructor === Object) {
          formData.append(key, JSON.stringify(data[key]))
        } else {
          formData.append(key, data[key])
        }
      } else {
        if (data[key] === 0) {
          formData.append(key, 0)
        } else {
          formData.append(key, '')
        }
      }
    }
  }
  return formData
}

