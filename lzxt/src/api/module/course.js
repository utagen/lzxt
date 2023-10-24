import axios from '../request'

export function getOnlineCourseList (grade, page) {
  return axios.post('/course/courseList', { page, pageSize: 100, subjectGrade: grade })
}

export function isInPractice (courseId) {
  return axios.post('/topic/practiceCourse', { courseId })
}

export function getPurchasedCourseList () {
  return axios.post('course/myCourse')
}

export function getLearnCourseList (page = 1, pageSize = 10) {
  return axios.post('/topic/practiceHistory', { page, pageSize })
}

export function getCourseById (courseId) {
  return axios.post('/course/courseDetail', { courseId })
}

export function getCourseSubjectById (courseId) {
  return axios.post('/course/subjectInfo', { courseId })
}

export function getTrialPractice (subjectId) {
  return axios.post('/topic/preview', { subjectId })
}
