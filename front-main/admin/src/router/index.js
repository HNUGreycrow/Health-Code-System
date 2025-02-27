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
    redirect: '/index',
    children: [{
      path: 'index',
      name: 'index',
      component: () => import('@/views/index'),
      meta: { title: '首页', icon: 'index' }
    }]
  },

  
  {
    path: '/place',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'place',
        component: () => import('@/views/place'),
        meta: { title: '场所码管理', icon: 'place' }
      }
    ]
  },
  {
    path: '/detect',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'detect',
        component: () => import('@/views/detect'),
        meta: { title: '核酸检测人员管理', icon: 'detect' }
      }
    ]
  },
  {
    path: '/admin',
    component: Layout,
    children: [
      {
        path: 'admin',
        name: 'admin',
        component: () => import('@/views/admin'),
        meta: { title: '管理员管理', icon: 'admin' }
      }
    ]
  },
  {
    path: '/healthcode',
    component: Layout,
    children: [
      {
        path: 'healthcode',
        name: 'healthcode',
        component: () => import('@/views/healthcode'),
        meta: { title: '转码管理', icon: 'healthcode' }
      }
    ]
  },
  {
    path: '/appeal',
    component: Layout,
    children: [
      {
        path: 'appeal',
        name: 'appeal',
        component: () => import('@/views/appeal'),
        meta: { title: '健康码申诉', icon: 'appeal' }
      }
    ]
  },
  {
    path: '/vac',
    component: Layout,
    children: [
      {
        path: 'vac',
        name: 'vac',
        component: () => import('@/views/vac'),
        meta: { title: '疫苗接种记录', icon: 'vac' }
      }
    ]
  },

 

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
