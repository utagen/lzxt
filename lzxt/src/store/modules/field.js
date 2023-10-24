import { loadBasicField, getMyCouponList } from '@/api/module/field'

const store = {
  namespaced: true,
  state: {
    grades: ['七年级', '八年级', '九年级'],
    subjects: [],
    terms: ['上', '下'],
    coupons: [],
    loaded: false
  },
  getters: {
    conditionGrades (state) {
      return ['全部'].concat(state.grades)
    },
    conditionSubjects (state) {
      return ['全部'].concat(state.subjects)
    },
    conditionTerms (state) {
      return ['全部'].concat(state.terms)
    },
    coupons (state) {
      if (state.coupons.length > 0) {
        return [].concat(state.coupons, [{ price: 0, name: '不使用优惠券' }])
      } else {
        return []
      }
    }
  },
  mutations: {
    setSubjects (state, list) {
      state.subjects = list
    },
    setGrades (state, list) {
      state.grades = list
    },
    setTerms (state, list) {
      state.terms = list
    },
    setCoupons (state, list) {
      state.coupons = list.map(item => item)
    }
  },
  actions: {
    loadBasicField ({ commit }) {
      return loadBasicField().then(({ subjectNameList, subjectGradeList, subjectTermList }) => {
        commit('setSubjects', subjectNameList)
        commit('setGrades', subjectGradeList)
        commit('setTerms', subjectTermList)
      })
    },
    getCouponList ({ commit }, courseId) {
      getMyCouponList(courseId).then(data => {
        commit('setCoupons', data)
      })
    }
  }
}

export default store
