import HomeLayout from '@/layout/home/Layout.vue'

import Home from '@/views/home/index.vue'
import { login, logout } from '@/api/module/user'

const HOME_INDEX_ROUTE = ''

const routes = {
  path: '/',
  component: HomeLayout,
  redirect: HOME_INDEX_ROUTE,
  children: [
    {
      path: HOME_INDEX_ROUTE,
      name: 'home',
      component: Home,
      meta: { title: '首页' }
    },
    {
      path: '/news',
      name: 'news-list',
      component: () => import(/* webpackChunkName: "service" */'@/views/service/ServiceList.vue'),
      meta: { title: '学堂资讯' },
      props: { service: 'news' }
    },
    {
      path: '/news/:articleId',
      name: 'news-info',
      component: () => import(/* webpackChunkName: "service" */'@/views/service/ServiceDetail.vue'),
      meta: { title: '资讯详情', parent: { title: '学堂资讯', route: '/news' } },
      props: route => ({ articleId: route.params.articleId, service: 'news' })
    },
    {
      path: '/cases',
      name: 'case-anlysis',
      component: () => import(/* webpackChunkName: "service" */'@/views/service/ServiceList.vue'),
      meta: { title: '案例与分析' },
      props: { service: 'cases' }
    },
    {
      path: '/cases/:articleId',
      name: 'case-info',
      component: () => import(/* webpackChunkName: "service" */'@/views/service/ServiceDetail.vue'),
      meta: { title: '案例详情', parent: { title: '案例与分析', route: '/cases' } },
      props: route => ({ articleId: route.params.articleId, service: 'cases' })
    },
    {
      path: '/courseAndService',
      name: 'course-service',
      component: () => import(/* webpackChunkName: "service" */'@/views/service/CourseService.vue'),
      meta: { title: '课程与服务' }
    }, {
      path: '/user/login',
      name: 'user-login',
      beforeEnter (to, ftom, next) {
        login().then(url => {
          location.href = url
        })
        next(false)
      },
      meta: { title: '用户登录' }
    }, {
      path: '/user/logout',
      name: 'user-logout',
      beforeEnter (to, ftom, next) {
        logout().then(() => {
          location.href = '/'
        })
        next(false)
      },
      meta: { title: '退出登录' }
    }
  ]
}

export default routes
