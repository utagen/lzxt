import {
  getUserWrongTopicList,
  getPractice,
  getCompletedPractice,
  submitAnswer,
  jumpQuestion,
  cancelPractice
} from '@/api/module/exam'

const store = {
  namespaced: true,
  state: {
    wrongTopicList: {
      list: [],
      total: 0
    },
    topic: {},
    statistics: {
      progress: 1,
      subjectUnitList: []
    },
    answerStatusList: [],
    viewIndex: 1,
    finished: false
  },
  mutations: {
    setWrongTopicList (state, { list, size }) {
      state.wrongTopicList = { list, total: size }
    },
    setTopic (state, topic) {
      state.topic = topic
    },
    setStatistics (state, statistics) {
      state.statistics = statistics
    },
    setAnswerStatusList (state, answerStatusList) {
      state.answerStatusList = answerStatusList
    },
    setViewIndex (state, index) {
      state.viewIndex = index
    },
    setFinished (state, finish = false) {
      state.finished = finish
    }
  },
  actions: {
    getUserWrongTopicList ({ commit }, params) {
      getUserWrongTopicList(params).then(data => {
        commit('setWrongTopicList', data)
      })
    },
    getPractice ({ commit }, { id, units, keep, practiceId }) {
      return getPractice(id, units, practiceId).then(data => {
        if (!keep) {
          commit('setTopic', data.topic)
          commit('setViewIndex', data.progress)
        }
        commit('setAnswerStatusList', data.topicAnswerStatusList)
        commit('setStatistics', Object.assign({}, data, { topic: null, topicAnswerStatusList: null }))

        return data
      })
    },
    getCompletedPractice ({ commit }, id) {
      return getCompletedPractice(id).then(data => {
        commit('setTopic', data.topic)
        commit('setAnswerStatusList', data.topicAnswerStatusList)
        commit('setStatistics', Object.assign({}, data, { topic: null, topicAnswerStatusList: null }))

        return data
      })
    },
    clearPractice ({ commit }) {
      commit('setTopic', {})
      commit('setAnswerStatusList', [])
      commit('setStatistics', { progress: 1, subjectUnitList: [] })
      commit('setViewIndex', 1)
    },
    jumQuestion ({ commit, state }, page) {
      return jumpQuestion(page, state.statistics.practiceId).then(data => {
        commit('setViewIndex', page)
        commit('setTopic', data.topic)
        return data
      })
    },
    submitAnswer ({ state, dispatch, commit }, { answer, id, units }) {
      return submitAnswer(answer, state.statistics.practiceId, state.topic.id).then(({ finish }) => {
        commit('setFinished', finish)
        return dispatch('getPractice', { id, units, keep: true, practiceId: state.statistics.practiceId }).then((data) => {
          return dispatch('jumQuestion', state.viewIndex)
        })
      })
    },
    cancelPractice ({ commit }, { practiceId }) {
      return cancelPractice(practiceId)
    }
  }
}

export default store
