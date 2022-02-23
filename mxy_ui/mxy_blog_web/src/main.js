import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/css/style.less'
import './assets/font/iconfont.css'
import './assets/font/Softbrush.css'
import './assets/font/League.css'
/*require('./Mock')*/
import {parseTime} from './utils'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);

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
import hljs from 'highlight.js'
//import 'highlight.js/styles/base16/atelier-plateau.css' //样式文件
import 'highlight.js/styles/gradient-light.css' //样式文件
//import 'highlight.js/styles/gradient-dark.css' //样式文件
Vue.directive('highlight',function (el) {
    let blocks = el.querySelectorAll('pre code');
    blocks.forEach((block)=>{
        hljs.highlightBlock(block)
    })
})
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
