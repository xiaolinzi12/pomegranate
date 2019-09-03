import {fetch, save, remove} from '@/utils/localStorage'
import {login, logout} from '@/api/login'
import {Message} from 'element-ui'


const state = {
  Authorization: fetch('Authorization') ? fetch('Authorization') : '',
  UserInfo: fetch('UserInfo') ? fetch('UserInfo') : {}
}

const mutations = {
// 修改token，并将token存入localStorage
  userLogin(state, user) {
    state.Authorization = user.Authorization;
    save('Authorization', user.Authorization);
  },
  userLogout(state) {
    state.Authorization = null
    remove('Authorization')
  }
}

const getters = {}

const actions = {

  handleLogin({commit, state}, loginForm) {
    return login(loginForm).then(res => {
      if (res.code === 200) {
        let userToken = res.data.token;
        commit('userLogin', {Authorization: userToken});
      } else {
        Message({
          message: res.msg,
          type: 'error',
          duration: 5 * 1000
        })
      }

    })
  },

  handleLogout({commit, state}) {
    return logout().then(res => {
        if (res.code === 200) {
          commit('userLogout');
        } else {
          Message({
            message: res.msg,
            type: 'error',
            duration: 5 * 1000
          })
        }
      }
    ).catch(error => {
      console.log(error)
      // this.errored = true
    })
  }


}


export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
