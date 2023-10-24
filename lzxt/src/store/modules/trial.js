import { getTrialPractice } from '@/api/module/course'

const store = {
  namespaced: true,
  state: {
    topicList: [],
    topic: {},
    statistics: {
      answered: 0,
      noAnswer: 0,
      trueNum: 0,
      total: 0,
      wrongNum: 0,
      subjectUnitList: []
    },
    answerStatusList: [],
    viewIndex: 1
  },
  mutations: {
    setTopic (state, topic) {
      state.topic = topic
    },
    setTopicList (state, list) {
      state.topicList = list
    },
    setStatistics (state, statistics) {
      state.statistics = { ...state.statistics, ...statistics }
    },
    setAnswerStatusList (state, answerStatusList) {
      state.answerStatusList = answerStatusList
    },
    setViewIndex (state, index) {
      state.viewIndex = index
    }

  },
  actions: {
    getPractice ({ commit }, { id }) {
      return getTrialPractice(id).then(data => {
        commit('setTopicList', [...data])
        const length = data.length
        if (length > 0) {
          commit('setTopic', { ...data[0] })
        }
        commit('setStatistics', { total: length, noAnswer: length })
        const answerStatusList = data.map((item, index) => ({ id: index + 1, pStatus: 0 }))
        commit('setAnswerStatusList', answerStatusList)
      })
    },
    clearPractice ({ commit }) {
      commit('setTopic', {})
      commit('setAnswerStatusList', [])
      commit('setStatistics', { answered: 0, noAnswer: 0, trueNum: 0, total: 0, wrongNum: 0, subjectUnitList: [] })
      commit('setViewIndex', 1)
    },
    jumQuestion ({ commit, state }, page) {
      const nextTopic = { ...state.topicList[page - 1] }
      commit('setTopic', nextTopic)
      commit('setViewIndex', page)
    },
    submitAnswer ({ commit, state }, { answer }) {
      let success = true
      if ([1, 2, 4].indexOf(state.topic.topicType) !== -1) {
        success = answer === state.topic.answer
      } else if (state.topic.topicType === 0) {
        success = answer.split('$#$').every((item, index) => {
          return item === state.topic.fillBlankAnswer[index].content
        })
      }
      const pStatus = success ? 2 : 1

      const nextTopic = { ...state.topic, userAnswer: answer, pstatus: pStatus }
      commit('setTopic', nextTopic)
      const nextList = [...state.topicList]
      nextList.splice(state.viewIndex - 1, 1, nextTopic)
      commit('setTopicList', nextList)
      const nextAnswerStatus = { index: state.viewIndex, pStatus: pStatus }
      const nextAnswerStatusList = [...state.answerStatusList]
      nextAnswerStatusList.splice(state.viewIndex - 1, 1, nextAnswerStatus)
      commit('setAnswerStatusList', nextAnswerStatusList)
      const { noAnswer, answered, trueNum, wrongNum } = state.statistics
      commit('setStatistics', {
        noAnswer: noAnswer - 1,
        answered: answered + 1,
        trueNum: pStatus === 2 ? trueNum + 1 : trueNum,
        wrongNum: pStatus === 1 ? wrongNum + 1 : wrongNum
      })
    }
  }
}

export default store
