import CryptoJS from 'crypto-js'

const SECRET_KEY = 'LZXT.COM'

function encodeParamsToUrl (param = {}) {
  try {
    return encodeURIComponent(CryptoJS.AES.encrypt(JSON.stringify(param), SECRET_KEY).toString())
  } catch (e) {
    console.warn(e.message)
  }
}

function decodeParamsFromUrl (ciphertext) {
  try {
    return JSON.parse(CryptoJS.AES.decrypt(decodeURIComponent(ciphertext), SECRET_KEY).toString(CryptoJS.enc.Utf8))
  } catch (e) {
    console.warn(e.message)
  }
}

function install (Vue) {
  Vue.prototype.$encode = encodeParamsToUrl
  Vue.prototype.$decode = decodeParamsFromUrl
}

export default { install }
