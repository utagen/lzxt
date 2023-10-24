import ExerciseLayout from '@/layout/exercise/Layout.vue'
import * as AuthRouteName from '../authentication-route'

const EXERCISE_INDEX_ROUTE = '/courses'

const routes = {
  path: '/exercise',
  component: ExerciseLayout,
  redirect: EXERCISE_INDEX_ROUTE,
  children: [{
    path: EXERCISE_INDEX_ROUTE,
    name: AuthRouteName.ONLINE_COURSE,
    component: () => import(/* webpackChunkName: "exercise" */'@/views/online/CourseList.vue'),
    meta: { title: '在线课程' }
  }, {
    path: '/purchased/courses',
    name: AuthRouteName.PURCHED_COURSES,
    component: () => import(/* webpackChunkName: "exercise" */'@/views/course/CouseList.vue'),
    meta: { title: '我的课程' }
  }, {
    path: '/learned/courses',
    name: AuthRouteName.LEARNED_COURSES,
    component: () => import(/* webpackChunkName: "exercise" */'@/views/learn/LearnList.vue'),
    meta: { title: '我的学习' }
  }, {
    path: '/courses/:course/choose/unit',
    name: AuthRouteName.CHOOSE_UNIT,
    component: () => import(/* webpackChunkName: "exercise" */'@/views/course/ChooseUnit.vue'),
    meta: { title: '选择单元' },
    props: true
  }, {
    path: '/courses/:subject/learning',
    name: AuthRouteName.COURSE_LEARNING,
    component: () => import(/* webpackChunkName: "exercise" */'@/views/course/LearnCourse.vue'),
    meta: { title: '练习详情' },
    props: route => ({ subject: route.params.subject, view: false })
  }, {
    path: '/practice/:practice/review',
    name: AuthRouteName.PRACTICE_REVIEW,
    component: () => import(/* webpackChunkName: "exercise" */'@/views/course/LearnCourse.vue'),
    meta: { title: '查看练习' },
    props: route => ({ subject: route.params.practice, view: true })
  }, {
    path: '/wrong/topics',
    name: AuthRouteName.WRONG_TOPICS,
    component: () => import(/* webpackChunkName: "exercise" */'@/views/wrong/Notebook.vue'),
    meta: { title: '错题本' }
  }, {
    path: '/orders',
    name: AuthRouteName.ORDER_LIST,
    component: () => import(/* webpackChunkName: "exercise" */'@/views/order/OrderList.vue'),
    meta: { title: '订单管理' }
  }, {
    path: '/create/:product/order',
    name: AuthRouteName.CREATE_ORDER,
    component: () => import(/* webpackChunkName: "exercise" */'@/views/order/CreateOrder.vue'),
    meta: { title: '创建订单' },
    props: true
  }, {
    path: '/pay/order/:order',
    name: AuthRouteName.PAY_ORDER,
    component: () => import(/* webpackChunkName: "exercise" */'@/views/order/PayOrder.vue'),
    meta: { title: '支付订单' },
    props: true
  }, {
    path: '/user/profile',
    name: AuthRouteName.USER_PROFILE,
    component: () => import(/* webpackChunkName: "exercise" */'@/views/user/Profile.vue'),
    meta: { title: '个人信息' }
  }, {
    path: '/trial/courses/:subject',
    name: AuthRouteName.TRIAL_EXERCISE,
    component: () => import(/* webpackChunkName: "exercise" */'@/views/course/LearnCourse.vue'),
    meta: { title: '免费试用' },
    props: route => ({ subject: route.params.subject, trial: true })
  }]
}

export default routes
