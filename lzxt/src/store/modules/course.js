import {
  getOnlineCourseList,
  getPurchasedCourseList,
  getLearnCourseList,
  getCourseById,
  getCourseSubjectById,
  isInPractice
} from '@/api/module/course'

const store = {
  namespaced: true,
  state: {
    onlineCourseList: {
      list: [],
      pageCount: 0,
      total: 0
    },
    purchasedCourseList: [],
    learnCourseList: {
      list: [],
      pageCount: 0,
      total: 0
    },
    course: {},
    courseSubjects: []
  },
  mutations: {
    setOnlineCourse (state, { list, pageCount, size }) {
      state.onlineCourseList = { list, pageCount, total: size }
    },
    setPurchasedCourse (state, courseList) {
      state.purchasedCourseList = courseList
    },
    setLearnCourse (state, { list, pageCount, total }) {
      state.learnCourseList = { list, pageCount, total }
    },
    setCourse (state, course) {
      state.course = course
    },
    setCourseSubjects (state, courseSubjects) {
      state.courseSubjects = courseSubjects
    }
  },
  actions: {
    getOnlineCourseList ({ commit }, { page, grade }) {
      getOnlineCourseList(grade, page).then(data => {
        commit('setOnlineCourse', data)
      })
    },
    isInPractice ({ commit }, id) {
      return isInPractice(id).then(({ isStudy, subjectId }) => {
        if (isStudy) {
          return subjectId
        }
        return Promise.reject(new Error('Not in progress'))
      })
    },
    getPurchasedCourseList ({ commit }) {
      getPurchasedCourseList().then(data => {
        commit('setPurchasedCourse', data)
      })
    },
    getLearnCourseList ({ commit }, { page, pageSize }) {
      getLearnCourseList(page, pageSize).then(data => {
        commit('setLearnCourse', data)
      })
    },
    getCourse ({ commit }, id) {
      getCourseById(id).then(data => {
        commit('setCourse', data)
      })
    },
    getCoursSubjects ({ commit }, id) {
      return getCourseSubjectById(id).then(data => {
        commit('setCourseSubjects', data)
        return data
      })
    }
  }
}

export default store
