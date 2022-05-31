import Vue from 'vue'
import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.scss' // 全局 css
import * as filters from './filters' // 全局过滤器
import * as echarts from 'echarts'
import App from './App'
import store from './store'
import router from './router'
import '@/icons' // 图标
import '@/permission' // 权限控制

// 如果想要中文版 element-ui，按如下方式声明
Vue.use(ElementUI)

import hljs from 'highlight.js'
import 'highlight.js/styles/gradient-light.css' //样式文件
Vue.directive('highlight', function (el) {
  let blocks = el.querySelectorAll('pre code');
  blocks.forEach((block) => {
    hljs.highlightBlock(block)
  })
})

// 注册全局实用过滤器
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.config.productionTip = false

// 挂载全局
Vue.prototype.$echarts = echarts

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
