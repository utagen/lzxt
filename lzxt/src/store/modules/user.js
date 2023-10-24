import { getUserInfo, createInviteeLink, updateProfile, logout, getPosterList } from '@/api/module/user'

const store = {
  namespaced: true,
  state: {
    profile: {},
    isAuthenticated: false,
    loginQRCodeUrl: '',
    loaded: false,
    posters: [],
	token:null,
  },
  mutations: {
    setProfile (state, profile) {
      state.profile = profile
      state.isAuthenticated = true
      state.loaded = true
    },
    clearProfile (state) {
      state.profile = {}
      state.isAuthenticated = false
      state.loaded = true
    },
    setQRcode (state, payload) {
      state.loginQRCodeUrl = payload
    },
    updateProfile (state, profile) {
      state.profile = { ...state.profile, ...profile }
    },
    setPosters (state, list) {
      state.posters = list
    }
  },
  actions: {
    getProfile ({ commit, state }) {
      if (state.loaded) return Promise.resolve(state.isAuthenticated)
      return getUserInfo().then(data => {
		commit('setProfile', data)
		return state.isAuthenticated
      }).catch(() => {
        commit('clearProfile')
        return state.isAuthenticated
      })
    },
    logout ({ commit }) {
      logout().then(() => {
        commit('clearProfile')
      })
    },
    createInviteeLink (store, postId) {
      return createInviteeLink(postId)
    },
    getPosterList ({ commit }) {
      return getPosterList().then(data => {
        commit('setPosters', data)
        return data
      })
    },
    updateProfile ({ commit }, profile) {
      return updateProfile(profile).then(() => {
        commit('updateProfile', profile)
      })
    }
  }
}

export default store
