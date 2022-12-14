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
      meta: {roles: ['system', 'user'], title: '仪表盘', icon: 'dashboard'}
    }]
  },
  {
    path: '/profile',
    component: Layout,
    redirect: '/profile/index',
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: {roles: ['system', 'user'], title: '个人中心', icon: 'user', noCache: true}
      }
    ]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/sysBlog',
    component: Layout,
    redirect: 'noRedirect',
    name: 'sysBlog',
    meta: {
      roles: ['system', 'user'],
      title: '博客系统',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'createBlog',
        component: () => import('@/views/blog/create'),
        name: 'createBlog',
        meta: {roles: ['system', 'user'], title: '发布博客', icon: 'el-icon-lollipop'}
      },
      {
        path: 'updateBlog',
        component: () => import('@/views/blog/update'),
        name: 'updateBlog',
        hidden: true,
        meta: {roles: ['system', 'user'], title: '编辑博客', icon: 'el-icon-lollipop'}
      },
      {
        path: 'blogList',
        component: () => import('@/views/blog/index.vue'),
        name: 'blogList',
        meta: {roles: ['system', 'user'], title: '博客管理', icon: 'el-icon-lollipop'}
      },
      {
        path: 'music',
        component: () => import('@/views/blog/music/music.vue'),
        name: 'music',
        meta: {roles: ['system'], title: '音乐管理', icon: 'el-icon-apple'}
      },
      {
        path: 'beautifulWords',
        component: () => import('@/views/blog/beautifulWords/index'),
        name: 'beautifulWords',
        meta: {roles: ['system'], title: '每日一句', icon: 'el-icon-grape'}
      },
      {
        path: 'webConfig',
        component: () => import('@/views/blog/config/index'),
        name: 'webConfig',
        meta: {roles: ['system'], title: '网站配置', icon: 'el-icon-lollipop'}
      }
    ]
  },
  {
    path: '/assist',
    component: Layout,
    redirect: 'noRedirect',
    name: 'assist',
    meta: {
      roles: ['system'],
      title: '辅助系统',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'codeGeneratorPath',
        component: () => import('@/views/assist/codeGenerator/index'),
        name: 'codeGeneratorName',
        meta: {roles: ['system'], title: '代码生成', icon: 'el-icon-lollipop'}
      },
      {
        path: 'esDataPath',
        component: () => import('@/views/es/index'),
        name: 'esDataName',
        meta: {roles: ['system'], title: 'ES数据存储', icon: 'el-icon-lollipop'}
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: 'noRedirect',
    name: 'system',
    meta: {
      roles: ['system'],
      title: '操作系统',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'user',
        component: () => import('@/views/sys/user/index'),
        name: 'user',
        meta: {roles: ['system'], title: '用户管理', icon: 'el-icon-apple'}
      },
      {
        path: 'role',
        component: () => import('@/views/sys/role/index'),
        name: 'role',
        meta: {roles: ['system'], title: '角色管理', icon: 'el-icon-cherry'}
      },
      {
        path: 'menu',
        component: () => import('@/views/sys/menu/index'),
        name: 'menu',
        meta: {roles: ['system'], title: '菜单管理', icon: 'el-icon-grape'}
      },
      {
        path: 'DictType',
        component: () => import('@/views/sys/dictType'),
        name: 'DictType',
        meta: {roles: ['system'], title: '业务字典', icon: 'el-icon-lollipop'}
      },
      {
        path: 'DictData',
        component: () => import('@/views/sys/dictData'),
        name: 'DictData',
        meta: {roles: ['system'], title: '字典细分', icon: 'el-icon-sugar'}
      },
      {
        path: 'job',
        component: () => import('@/views/sys/job/index'),
        name: 'job',
        meta: {roles: ['system'], title: '定时任务', icon: 'el-icon-watermelon'}
      },
      {
        path: 'config',
        component: () => import('@/views/sys/config/index'),
        name: 'config',
        meta: {roles: ['system'], title: '系统参数', icon: 'el-icon-watermelon'}
      },
      {
        path: 'country',
        component: () => import('@/views/sys/country/index'),
        name: 'country',
        meta: {roles: ['system'], title: '国家省分', icon: 'el-icon-watermelon'}
      }
    ]
  },
  {
    path: '/sysFile',
    component: Layout,
    redirect: 'noRedirect',
    name: 'sysFile',
    meta: {
      roles: ['system'],
      title: '文件系统',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'filePath',
        component: () => import('@/views/file/file/index'),
        name: 'fileName',
        meta: {roles: ['system'], title: '文件管理', icon: 'el-icon-lollipop'}
      },
      {
        path: 'imagePath',
        component: () => import('@/views/file/image/index'),
        name: 'imageName',
        meta: {roles: ['system'], title: '图片管理', icon: 'el-icon-lollipop'}
      }
    ]
  },
  {
    path: '/report',
    component: Layout,
    redirect: 'noRedirect',
    name: 'report',
    meta: {
      roles: ['system'],
      title: '报表统计',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'index1Path',
        component: () => import('@/views/echarts/index1'),
        name: 'index1Name',
        meta: {roles: ['system'], title: '报表样例一', icon: 'el-icon-lollipop'}
      }, {
        path: 'index2Path',
        component: () => import('@/views/echarts/index2'),
        name: 'index2Name',
        meta: {roles: ['system'], title: '报表样例二', icon: 'el-icon-lollipop'}
      }, {
        path: 'index3Path',
        component: () => import('@/views/echarts/index3'),
        name: 'index3Name',
        meta: {roles: ['system'], title: '报表样例三', icon: 'el-icon-lollipop'}
      }, {
        path: 'index4Path',
        component: () => import('@/views/echarts/index4'),
        name: 'index4Name',
        meta: {roles: ['system'], title: '报表样例四', icon: 'el-icon-lollipop'}
      }
    ]
  },
  {
    path: '/components',
    component: Layout,
    redirect: 'noRedirect',
    name: 'components',
    meta: {
      roles: ['system'],
      title: '组件示例',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'wangEditorPath',
        component: () => import('@/views/components/wangEditor'),
        name: 'wangEditorName',
        meta: {roles: ['system'], title: 'wangEditor编辑器', icon: 'el-icon-watermelon'}
      },
      {
        path: 'treeTable',
        component: () => import('@/views/tree/index'),
        name: 'treeTableName',
        meta: {roles: ['system'], title: '树形表格', icon: 'el-icon-watermelon'}
      }
    ]
  },
  {
    path: '/collection',
    component: Layout,
    redirect: 'noRedirect',
    name: 'collection',
    meta: {
      roles: ['system'],
      title: '收藏系统',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'bookmarkPath',
        component: () => import('@/views/collection/bookmark/index'),
        name: 'bookmarkName',
        meta: {roles: ['system'], title: '书签管理', icon: 'el-icon-lollipop'}
      }
    ]
  },
  {
    path: '/sysMonitoring',
    component: Layout,
    redirect: 'noRedirect',
    name: 'monitoring',
    meta: {
      roles: ['system'],
      title: '监控系统',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'druid',
        component: () => import('@/views/monitoring/druid/index'),
        name: 'druidName',
        meta: {roles: ['system'], title: 'Druid监控', icon: 'el-icon-lollipop'}
      },
      {
        path: 'redis',
        component: () => import('@/views/monitoring/redis/index'),
        name: 'redisName',
        meta: {roles: ['system'], title: 'Redis监控', icon: 'el-icon-lollipop'}
      },
      {
        path: 'sysInfo',
        component: () => import('@/views/monitoring/sysInfo/index'),
        name: 'sysInfo',
        meta: {roles: ['system'], title: '系统监控', icon: 'el-icon-watermelon'}
      },
    ]
  },
  {
    path: '/sysLog',
    component: Layout,
    redirect: 'noRedirect',
    name: 'sysLog',
    meta: {
      roles: ['system'],
      title: '日志系统',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'loginLog',
        component: () => import('@/views/log/loginLog/index'),
        name: 'loginLog',
        meta: {roles: ['system'], title: '登录日志', icon: 'el-icon-watermelon'}
      },
      {
        path: 'log',
        component: () => import('@/views/log/operLog/index'),
        name: 'log',
        meta: {roles: ['system'], title: '操作日志', icon: 'el-icon-watermelon'}
      },
      {
        path: 'smsLog',
        component: () => import('@/views/log/smsLog/SysSmsSendLog'),
        name: 'smsLog',
        meta: {roles: ['system'], title: '短信日志', icon: 'el-icon-watermelon'}
      },
      {
        path: 'blogLog',
        component: () => import('@/views/log/blogLog/index'),
        name: 'blogLog',
        meta: {roles: ['system'], title: '博客日志', icon: 'el-icon-lollipop'}
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
