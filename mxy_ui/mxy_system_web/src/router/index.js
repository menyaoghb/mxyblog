import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/* Router Modules */
import componentsRouter from './modules/components'
import chartsRouter from './modules/charts'
import tableRouter from './modules/table'
import nestedRouter from './modules/nested'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   如果设置为true，条目将不会显示在侧栏中(默认为false)
 * alwaysShow: true               如果设置为true，将始终显示根菜单
 *                                如果没有设置alwaysShow，当item有多个子路由时，
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
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
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: {title: '首页', icon: 'el-icon-loading', affix: true}
      }
    ]
  },
  /*  {
      path: '/documentation',
      component: Layout,
      children: [
        {
          path: 'index',
          component: () => import('@/views/documentation/index'),
          name: 'Documentation',
          meta: { title: 'Documentation', icon: 'documentation', affix: true }
        }
      ]
    },*/
  /*  {
      path: '/guide',
      component: Layout,
      redirect: '/guide/index',
      children: [
        {
          path: 'index',
          component: () => import('@/views/guide/index'),
          name: 'Guide',
          meta: { title: 'Guide', icon: 'guide', noCache: true }
        }
      ]
    },*/
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
        meta: {title: 'Profile', icon: 'user', noCache: true}
      }
    ]
  },
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
    path: '/web',
    component: Layout,
    redirect: 'noRedirect',
    name: 'configPages',
    meta: {
      title: '网站管理',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'webView',
        component: () => import('@/views/config/sysConfig/index'),
        name: 'sysConfigName',
        meta: {title: '首页预览', icon: 'el-icon-lollipop'}
      },
      {
        path: 'createBlog',
        component: () => import('@/views/web/blog/create'),
        name: 'sysConfigName',
        meta: {title: '创建博客', icon: 'el-icon-lollipop'}
      },
      {
        path: 'blogList',
        component: () => import('@/views/web/blog/index'),
        name: 'sysConfigName',
        meta: {title: '博客管理', icon: 'el-icon-lollipop'}
      },
      {
        path: 'webConfig',
        component: () => import('@/views/config/sysConfig/index'),
        name: 'sysConfigName',
        meta: {title: '网站配置', icon: 'el-icon-lollipop'}
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
  }, {
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
  {
    path: '/config',
    component: Layout,
    redirect: 'noRedirect',
    name: 'configPages',
    meta: {
      title: '系统配置',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'sysConfig',
        component: () => import('@/views/config/sysConfig/index'),
        name: 'sysConfigName',
        meta: {title: '平台参数', icon: 'el-icon-lollipop'}
      }
    ]
  },
  {
    path: '/test',
    component: Layout,
    redirect: 'noRedirect',
    name: 'DictPages',
    meta: {
      title: '测试页面',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'test',
        component: () => import('@/views/test/index'),
        name: 'DictType',
        meta: {title: '报表一', icon: 'el-icon-lollipop'}
      },
      {
        path: 'test2',
        component: () => import('@/views/test/index2'),
        name: 'DictData',
        meta: {title: '报表二', icon: 'el-icon-sugar'}
      },
      {
        path: 'home',
        component: () => import('@/views/index/home'),
        name: 'home',
        meta: {title: '欢迎页', icon: 'el-icon-loading'}
      }
    ]
  },
  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true}
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
    path: '/web',
    component: Layout,
    redirect: 'noRedirect',
    name: 'configPages',
    meta: {
      title: '网站管理',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'webView',
        component: () => import('@/views/config/sysConfig/index'),
        name: 'sysConfigName',
        meta: {title: '首页预览', icon: 'el-icon-lollipop'}
      },
      {
        path: 'createBlog',
        component: () => import('@/views/web/blog/create'),
        name: 'sysConfigName',
        meta: {title: '创建博客', icon: 'el-icon-lollipop'}
      },
      {
        path: 'blogList',
        component: () => import('@/views/web/blog/index'),
        name: 'sysConfigName',
        meta: {title: '博客管理', icon: 'el-icon-lollipop'}
      },
      {
        path: 'webConfig',
        component: () => import('@/views/config/sysConfig/index'),
        name: 'sysConfigName',
        meta: {title: '网站配置', icon: 'el-icon-lollipop'}
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
  }, {
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
  {
    path: '/config',
    component: Layout,
    redirect: 'noRedirect',
    name: 'configPages',
    meta: {
      title: '系统配置',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'sysConfig',
        component: () => import('@/views/config/sysConfig/index'),
        name: 'sysConfigName',
        meta: {title: '平台参数', icon: 'el-icon-lollipop'}
      }
    ]
  },
  {
    path: '/test',
    component: Layout,
    redirect: 'noRedirect',
    name: 'DictPages',
    meta: {
      title: '测试页面',
      icon: 'el-icon-orange'
    },
    children: [
      {
        path: 'test',
        component: () => import('@/views/test/index'),
        name: 'DictType',
        meta: {title: '报表一', icon: 'el-icon-lollipop'}
      },
      {
        path: 'test2',
        component: () => import('@/views/test/index2'),
        name: 'DictData',
        meta: {title: '报表二', icon: 'el-icon-sugar'}
      },
      {
        path: 'home',
        component: () => import('@/views/index/home'),
        name: 'home',
        meta: {title: '欢迎页', icon: 'el-icon-loading'}
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
