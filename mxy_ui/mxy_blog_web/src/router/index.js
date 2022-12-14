import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import('../views/Home.vue'),
        meta: { title: '首页'}
    },
    {
        path: '/blog',
        name: 'blog',
        component: () => import('../views/Blog.vue'),
        meta: { title: '博客'}
    },
    {
        path: '/MuiPlayer',
        name: 'MuiPlayer',
        component: () => import('../views/MuiPlayer.vue'),
        meta: { title: '视频'}
    },
    {
        path: '/music',
        name: 'music',
        component: () => import('../views/Music.vue'),
        meta: { title: '音乐'}
    },
    {
        path: '/index',
        name: 'index',
        component: () => import('../views/Index.vue'),
        meta: { title: '研发ing'}
    },
    {
        path: '/Original',
        name: 'Original',
        component: () => import('../views/Original.vue'),
        meta: { title: '原创'}
    },
    {
        path: '/Photo',
        name: 'Photo',
        component: () => import('../views/Photo.vue'),
        meta: { title: '相册'}
    },
    {
        path: '/Collection',
        name: 'Collection',
        component: () => import('../views/Collection.vue'),
        meta: { title: '收藏'}
    },
    {
        path: '/Diary',
        name: 'Diary',
        component: () => import('../views/Diary.vue'),
        meta: { title: '成长'}
    },
    {
        path: '/category/:cate',
        name: 'category',
        component: () => import('../views/Home.vue'),
        meta: { title: '分类', params: 'cate'}
    },
    {
        path: '/search/:words',
        name: 'search',
        component: () => import('../views/Home.vue'),
        meta: { title: '搜索', params: 'words'}
    },
    {
        path: '/about',
        name: 'about',
        component: () => import('../views/About.vue'),
        meta: { title: '关于'}
    },
    {
        path: '/friend',
        name: 'friend',
        component: () => import('../views/Friend.vue'),
        meta: { title: '友链'}
    },
    {
        path: '/article/:id',
        name: 'article',
        component: () => import('../views/Articles.vue'),
        meta: { title: '文章'}
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})
router.beforeEach((to, from, next) => {
    let title = '星辰大海·征途在前'
    if (to.meta.params){
        title = `${to.meta.title}:${to.params[to.meta.params] || ''} - ${title}`
    }else {
        title = `${to.meta.title} - ${title}`
    }
    document.title = title
    if (to.path !== from.path) {
        store.dispatch('setLoading', true);
    }
    next();
})
router.afterEach((to, from) => {
    // 最多延迟 关闭 loading
    setTimeout(() => {
        store.dispatch('setLoading', false);
    }, 1500)
})
export default router
