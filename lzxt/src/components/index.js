const modulesFiles = require.context('.', true, /\.vue$/)

// you do not need `import app from './modules/app'`
// it will auto require all vuex module from modules file
const components = modulesFiles.keys().map(modulesFile => {
  return modulesFiles(modulesFile).default
})

function install (Vue) {
  components.forEach(component => {
    Vue.component(component.name, component)
  })
}

export default { install }
