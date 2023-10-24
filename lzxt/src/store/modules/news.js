import { getNewsList, getNewsArticle } from '@/api/module/service'

const mapper = ['questionBankList', 'promoteStatusList', 'otherList']
const getStateListName = type => {
  return mapper[type - 1]
}

const store = {
  namespaced: true,
  state: {
    questionBankList: {
      list: [],
      pageCount: 0,
      total: 0
    },
    promoteStatusList: {
      list: [],
      pageCount: 0,
      total: 0
    },
    otherList: {
      list: [],
      pageCount: 0,
      total: 0
    },
    newsType: 1,
    article: {
      id: '',
      title: '',
      content: '',
      author: '',
      createTime: '',
      summary: ''
    }
  },
  getters: {
    tabOneList (state) {
      return [].slice.call(state.questionBankList.list, 0, 5)
    },
    tabTwoList (state) {
      return [].slice.call(state.promoteStatusList.list, 0, 5)
    },
    tabThreeList (state) {
      return [].slice.call(state.otherList.list, 0, 5)
    },
    newsList (state) {
      return state[getStateListName(state.newsType)].list
    },
    total (state) {
      return state[getStateListName(state.newsType)].total
    }
  },
  mutations: {
    initNewsState (state, { newsType, list, pageCount, total }) {
      state.newsType = newsType
      state[getStateListName(newsType)] = { list, pageCount, total }
    },
    setArticle (state, data) {
      state.article = data
    }
  },
  actions: {
    getNewsList ({ commit }, { newsType, page }) {
      if ([1, 2, 3].indexOf(newsType) !== -1) {
        getNewsList(newsType, page, 10).then(data => {
          const { list, pageCount, size } = data
          commit('initNewsState', { newsType: newsType, list, pageCount, total: size })
        })
      }
    },
    getArticle ({ commit }, id) {
      getNewsArticle(id).then(data => {
        commit('setArticle', data)
      })
    }
  }
}

export default store
