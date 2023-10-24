import { getCaseList, getCaseArticle } from '@/api/module/service'

const store = {
  namespaced: true,
  state: {
    caseList: {
      list: [],
      total: 0
    },
    article: {
      id: '',
      title: '',
      content: '',
      author: '',
      createTime: '',
      summary: ''
    }
  },
  mutations: {
    setCaseList (state, { list, total }) {
      state.caseList = { list, total }
    },
    setArticle (state, data) {
      state.article = data
    }
  },
  actions: {
    getCaseList ({ commit }, { page, pageSize }) {
      return getCaseList(page, pageSize).then(data => {
        commit('setCaseList', data)
        return data
      })
    },
    getArticle ({ commit }, id) {
      getCaseArticle(id).then(data => {
        commit('setArticle', data)
      })
    }
  }
}

export default store
