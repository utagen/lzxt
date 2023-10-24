import Vue from 'vue'
import VueRouter from 'vue-router'
import AuthRouteName from './authentication-route'

import home from './home/routes'
import exercise from './exercise/routes'
import mobile from './mobile/routes'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: process.env.VUE_APP_BASE_URL,
  routes: [home, exercise, mobile, {
    path: '*',
    redirect: '/'
  }]
})

router.beforeEach(async (to, from, next) => {
  const store = router.app.$options.store
  const title = ['励志学堂']
  if (to.meta && to.meta.title) {
    title.unshift(to.meta.title)
  }
  document.title = title.join(' - ')
  const isAuthenticated = await store.dispatch('user/getProfile')
  if (AuthRouteName.indexOf(to.name) === -1) {
    next()
  } else if (isAuthenticated) {
    next()
  } else {
    next({ name: 'user-login' })
  }
})

export default router
