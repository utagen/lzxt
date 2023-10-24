import Vue from 'vue'
import Element from 'element-ui'
import 'normalize.css/normalize.css'
import './theme/index.css'
import './styles/app.scss'

import components from './components'
import plugins from './plugins'

import router from './router'
import store from './store'

import App from '@/views/App.vue'

Vue.config.productionTip = false
Vue.use(Element, { size: 'small' })
Vue.use(components)
Vue.use(plugins)

console.log(process.env.VUE_APP_API_BASE_URL)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
