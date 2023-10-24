import axios from '../request'

export function getOrderList (page = 1, pageSize = 10) {
  return axios.post('/order/orderList', { page, pageSize })
}

export function deleteOrder (orderId) {
  return axios.post('/order/delete', { orderId })
}

export function createOrder (params) {
  return axios.post('/order/submitOrder', params)
}

export function getWebchatPayUrl (orderId) {
  return axios.post('/order/wxPay', { orderId, payType: 1 })
}

export function getOrderById (orderId) {
  return axios.post('/order/findOrder', { orderId })
}

export function getMpWechartPay (orderId) {
  return axios.post('/order/wxMpPay', { orderId, payType: 1 })
}
