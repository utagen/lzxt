function random () {
  return Math.floor(Math.random() * 1000)
}

function generate (prefix = 'id') {
  return [prefix, random(), random()].join('_')
}

function install (Vue) {
  Vue.prototype.$idBuilder = generate
}

export default { install }
