import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/css/style.less'
import './assets/font/iconfont.css'
/*require('./Mock')*/
import {parseTime} from './utils'

Vue.config.productionTip = false
Vue.filter('parseTime', (v) => parseTime(v, '{y}-{m}-{d} {h}:{i}:{s}'))
Vue.filter('unescape', function (value) {
    if (!value) return ''
    value = value.toString()
    return value.replace(value ? /&(?!#?\w+;)/g : /&/g, '&amp;')
        .replace(/&lt;/g, "<")
        .replace(/&gt;/g, ">")
        .replace(/&quot;/g, "\"")
        .replace(/&#39;/g, "\'");
})
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
