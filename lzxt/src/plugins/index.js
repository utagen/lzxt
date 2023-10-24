import compareFn from '@/helper/sort'

const modulesFiles = require.context('./modules', false, /\.js$/)

// you do not need `import app from './modules/app'`
// it will auto require all vuex module from modules file
const plugins = modulesFiles.keys().map(filename => {
  return modulesFiles(filename).default
})

function install (Vue) {
  plugins.forEach(plugin => Vue.use(plugin))

  Vue.prototype.$compareFn = compareFn
}

export default { install }
