function toTwoDigest (value) {
  return value < 10 ? `0${value}` : value
}

function formatDate (now) {
  const year = now.getFullYear()
  const month = now.getMonth() + 1
  const date = now.getDate()
  const hour = now.getHours()
  const min = now.getMinutes()
  const second = now.getSeconds()
  return `${year}-${toTwoDigest(month)}-${toTwoDigest(date)} ${toTwoDigest(hour)}:${toTwoDigest(min)}:${toTwoDigest(second)}`
}

function install (Vue) {
  Vue.prototype.$date = {
    format: formatDate
  }
}

export default { install }
