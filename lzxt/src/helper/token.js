const STORAGE_TOKEN_KET = 'Authentication'

export function getToken () {
  return localStorage.getItem(STORAGE_TOKEN_KET) || ''
}

export function setToken (token = '') {
  localStorage.setItem(STORAGE_TOKEN_KET, token)
}

export function clearToken () {
  localStorage.removeItem(STORAGE_TOKEN_KET)
}
