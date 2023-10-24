function weixinJSBridge () {
  return new Promise(resolve => {
    const onBridgeReady = () => {
      resolve(window.WeixinJSBridge)
    }
    if (typeof window.WeixinJSBridge === 'undefined') {
      if (document.addEventListener) {
        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false)
      } else if (document.attachEvent) {
        document.attachEvent('WeixinJSBridgeReady', onBridgeReady)
        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady)
      }
    } else {
      onBridgeReady()
    }
  })
}

function weixinPay ({ appId, timeStamp, nonceStr, packageValue, paySign, signType }) {
  return new Promise((resolve, reject) => {
    weixinJSBridge().then(WeixinJSBridge => {
      WeixinJSBridge.invoke('getBrandWCPayRequest', { appId, timeStamp, nonceStr, package: packageValue, paySign, signType }, function (res) {
        resolve({ message: '支付成功' })
      })
    })
  })
}

function install (Vue) {
  Vue.prototype.$weixin = /MicroMessenger/ig.test(navigator.userAgent.toLowerCase())
  Vue.prototype.$weixinPay = weixinPay
}

export default { install }
