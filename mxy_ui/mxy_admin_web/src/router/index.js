import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: {title: '首页', icon: 'dashboard'}
    }]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/user',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/user/index'),
        name: 'user',
        meta: {title: '用户管理', icon: 'el-icon-apple'}
      }
    ]
  },
  {
    path: '/role',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/role/index'),
        name: 'user',
        meta: {title: '角色管理', icon: 'el-icon-cherry'}
      }
    ]
  },
  {
    path: '/menu',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/menu/index'),
        name: 'menu',
        meta: {title: '菜单管理', icon: 'el-icon-grape'}
      }
    ]
  },
  {
    path: '/dict',
    component: Layout,
    redirect: 'noRedirect',
    name: 'DictPages',
    meta: {
      title: '字典管理',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'type',
        component: () => import('@/views/dict/type/index'),
        name: 'DictType',
        meta: {title: '字典类型', icon: 'el-icon-lollipop'}
      },
      {
        path: 'data',
        component: () => import('@/views/dict/data/index'),
        name: 'DictData',
        meta: {title: '字典数据', icon: 'el-icon-sugar'}
      }
    ]
  },
  {
    path: '/beautifulWords',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/mxy/beautifulWords/index'),
        name: 'beautifulWords',
        meta: {title: '佳句记忆', icon: 'el-icon-grape'}
      }
    ]
  },
  {
    path: '/file',
    component: Layout,
    redirect: 'noRedirect',
    name: 'filePages',
    meta: {
      title: '文件管理',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'filePath',
        component: () => import('@/views/file/file/index'),
        name: 'fileName',
        meta: {title: '文件管理', icon: 'el-icon-lollipop'}
      },
      {
        path: 'imagePath',
        component: () => import('@/views/file/image/index'),
        name: 'imageName',
        meta: {title: '图片管理', icon: 'el-icon-lollipop'}
      }
    ]
  },
  {
    path: '/monitoring',
    component: Layout,
    redirect: 'noRedirect',
    name: 'filePages',
    meta: {
      title: '监控中心',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'druid',
        component: () => import('@/views/monitoring/druid/index'),
        name: 'druidName',
        meta: {title: 'Druid监控', icon: 'el-icon-lollipop'}
      },
      {
        path: 'redis',
        component: () => import('@/views/monitoring/druid/index'),
        name: 'redisName',
        meta: {title: 'Redis监控', icon: 'el-icon-lollipop'}
      }
    ]
  },
  {
    path: '/xxl',
    component: Layout,
    children: [
      {
        path: 'job',
        component: () => import('@/views/job/index'),
        name: 'log',
        meta: {title: '定时任务', icon: 'el-icon-watermelon'}
      }
    ]
  },
  {
    path: '/log',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/log/index'),
        name: 'log',
        meta: {title: '操作日志', icon: 'el-icon-watermelon'}
      }
    ]
  },
  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true}
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
