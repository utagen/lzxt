import axios from 'axios'
import { getToken, setToken, clearToken } from '../helper/token'
import router from '../router/index'
import AuthRouteName from '../router/authentication-route'

export const SUCCESS = 2000000000
export const GUEST_USER = 1000000200

console.log(process.env)

// create an axios instance
const service = axios.create({
  /**
   * `baseURL` will be prepended to `url` unless `url` is absolute. It can be convenient to set `baseURL` for an instance of axios pass relative URLs to methods of that instance.
   */
  baseURL: process.env.VUE_APP_API_BASE_URL,

  /**
     *
     */
  headers: {
    'Content-Type': 'application/json'
  },

  /**
   * `transformRequest` allows changes to the request data before it is sent to the server This is only applicable for request methods 'PUT', 'POST', and 'PATCH' The last function in the array must return a string or an instance of Buffer, ArrayBuffer, FormData or Stream
   */
  transformRequest: [function transformRequest (data) {
    // Do whatever you want to transform the data
    return JSON.stringify(data)
  }],

  /**
   * `transformResponse` allows changes to the response data to be made before it is passed to then/catch
   */
  transformResponse: [function transformResponse (data) {
    // Do whatever you want to transform the data
    if (Object.prototype.toString.call(data) === '[object String]') {
      return JSON.parse(data)
    }
    return data
  }],

  /**
   * `responseType` indicates the type of data that the server will respond with options are 'arraybuffer', 'blob', 'document', 'json', 'text', 'stream'
   */
  responseType: 'json',

  withCredentials: true
})

service.interceptors.request.use(config => {
  const token = getToken()
  if (token) {
    config.headers['mer-auth'] = token
  }
  return config
})

service.interceptors.response.use(response => {
  if (response.headers['refresh-auth']) {
    setToken(response.headers['refresh-auth'])
  }
  if (!response.data) {
    return Promise.reject(new Error('Network error!'))
  }

  if (response.data.code === SUCCESS) {
    return response.data.result
  } else {
    switch (response.data.code) {
      case GUEST_USER:
        clearToken()
        if (AuthRouteName.indexOf(router.currentRoute.name) !== -1) {
          router.push({ name: 'home' })
        }
        break
      default:
    }
    return Promise.reject(response.data)
  }
})

export default service
