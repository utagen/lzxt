import axios from '../request'

export function getUserWrongTopicList ({ page, pageSize, subjectGrade, subjectTerm, subjectName }) {
  return axios.post('/topic/userProblemSearch', {
    page: page || 1,
    pageSize: pageSize || 10,
    subjectTerm: subjectTerm || '全部',
    subjectGrade: subjectGrade || '全部',
    subjectName: subjectName || '全部'
  })
}

export function getPractice (subjectId, subjectUnitList = [], practiceId) {
  const params = { subjectId, subjectUnitList }
  if (practiceId) {
    params.practiceId = practiceId
  }
  return axios.post('/topic/practice', params)
}

export function getCompletedPractice (practiceId) {
  return axios.post('/topic/practiceDetail', { practiceId })
}

export function submitAnswer (answer, practiceId, topicId) {
  return axios.post('/topic/submit', { answer, practiceId, topicId })
}

export function jumpQuestion (page, practiceId) {
  return axios.post('/topic/jump', { page, practiceId })
}

export function cancelPractice (practiceId) {
  return axios.post('/topic/cancel', { practiceId })
}
