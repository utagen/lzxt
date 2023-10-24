import axios from '../request'

export function loadBasicField () {
  return axios.post('subject/listSubjectNew')
}

export function getMyCouponList (courseId) {
  return axios.post('/course/myCoupon', { courseId })
}
