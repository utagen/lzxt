import axios from '../request'

export function getNewsList (tab = 1, page = 1, pageSize = 10) {
  return axios.post('/news/newsList', { tab, page, pageSize })
}

export function getCaseList (page = 1, pageSize = 10) {
  return axios.post('/case/findCaseList', { page, pageSize })
}

export function getNewsArticle (id) {
  return axios.post('/news/detail', { id })
}

export function getCaseArticle (id) {
  return axios.post('/case/findCase', { id })
}
