import {
  getOrderList,
  deleteOrder,
  createOrder,
  getWebchatPayUrl,
  getOrderById,
  getMpWechartPay
} from '@/api/module/order'

const store = {
  namespaced: true,
  state: {
    orderList: {
      list: [],
      total: 0
    },
    webchatPayUrl: '',
    orderInfo: {},
    weixinPay: {}
  },
  mutations: {
    setOrderList (state, { list, size }) {
      state.orderList = { list, total: size }
    },
    setWebchatPayUrl (state, payUrl) {
      state.webchatPayUrl = payUrl
    },
    setOrderInfo (state, order) {
      state.orderInfo = order
    },
    setWeixinpay (state, data) {
      state.weixinPay = data
    }
  },
  actions: {
    getOrderList ({ commit }, params) {
      getOrderList(params.page, params.pageSize).then(data => {
        commit('setOrderList', data)
      })
    },
    deleteOrder (store, orderId) {
      return deleteOrder(orderId)
    },
    createOrder (store, params) {
      return createOrder(params)
    },
    getWebchatPayUrl ({ commit }, orderId) {
      return getWebchatPayUrl(orderId).then(webchatPayUrl => {
        commit('setWebchatPayUrl', webchatPayUrl)
      })
    },
    getOrderById ({ commit }, orderId) {
      return getOrderById(orderId).then(data => {
        commit('setOrderInfo', data)
        return data
      })
    },
    getWeixinPay ({ commit }, orderId) {
      return getMpWechartPay(orderId).then(data => {
        commit('weixinpay', data)
        return data
      })
    }
  }
}

export default store
