import axios from '../request'

export function getUserInfo () {
  return axios.post('/sso/user/userInfo')
}

export function login () {
  return axios.post('/sso/login/getQRCodeUrl')
}

export function createInviteeLink (id) {
  return axios.post('/i/gen', { id })
}

export function updateProfile (profile) {
  return axios.post('/sso/user/updateUser', profile)
}

export function logout () {
  return axios.post('/sso/user/logout')
}

export function getPosterList (page = 1, pageSize = 10) {
  return axios.get('/i/all', { page, pageSize })
}
