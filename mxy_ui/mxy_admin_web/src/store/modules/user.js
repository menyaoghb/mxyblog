import {login, phoneLogin, emailLogin, thirdLogin, logout, getInfo} from '@/api/login/user'
import {getToken, setToken, removeToken} from '@/utils/auth'
import {resetRouter} from '@/router'
import {Message} from "element-ui";


const getDefaultState = () => {
  return {
    token: getToken(),
    userId: '',
    name: '',
    nickName: '',
    email: '',
    phoneNumber: '',
    sex: '',
    avatar: '',
    loginIp: '',
    loginDate: '',
    remark: '',
    roles: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_USER_ID: (state, userId) => {
    state.userId = userId
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_NICKNAME: (state, nickName) => {
    state.nickName = nickName
  },
  SET_EMAIL: (state, email) => {
    state.email = email
  },
  SET_PHONE_NUMBER: (state, phoneNumber) => {
    state.phoneNumber = phoneNumber
  },
  SET_SEX: (state, sex) => {
    state.sex = sex
  },
  SET_LOGIN_IP: (state, loginIp) => {
    state.loginIp = loginIp
  },
  SET_LOGIN_DATE: (state, loginDate) => {
    state.loginDate = loginDate
  },
  SET_REMARK: (state, remark) => {
    state.remark = remark
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

const actions = {
  // user login
  login({commit}, userInfo) {
    const {username, password, key, captcha} = userInfo
    return new Promise((resolve, reject) => {
      login({username: username.trim(), password: password, key: key, captcha: captcha}).then(response => {
        commit('SET_TOKEN', response.token)
        setToken(response.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // phone login
  phoneLogin({commit}, userInfo) {
    const {phoneNo, code} = userInfo
    return new Promise((resolve, reject) => {
      phoneLogin({phone: phoneNo.trim(), code: code.trim()}).then(response => {
        commit('SET_TOKEN', response.token)
        setToken(response.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // phone login
  emailLogin({commit}, userInfo) {
    const {email, code} = userInfo
    return new Promise((resolve, reject) => {
      emailLogin({email: email.trim(), code: code.trim()}).then(response => {
        commit('SET_TOKEN', response.token)
        setToken(response.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // ???????????????
  thirdLogin({commit}, userInfo) {
    const {source} = userInfo
    return new Promise((resolve, reject) => {
      thirdLogin({source: source.trim()}).then(response => {
        console.log("?????????????????????", response)
        if (response.data.code === "200") {
          window.location.href = response.data.url
        } else {
          Message.warning(response.data.msg);
        }
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({commit, state}) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const {data} = response
        const {user} = response
        if (!data) {
          reject('??????????????????????????????.')
        }
        let roles = [];
        for (let i = 0; i < data.authorities.length; i++) {
          roles.push(data.authorities[i].authority);
        }
        if (!roles || roles.length <= 0) {
          reject('????????????????????????!')
        }
        data.roles = roles;
        commit('SET_ROLES', data.roles)
        commit('SET_USER_ID', data.userId)
        commit('SET_NAME', data.username)
        commit('SET_AVATAR', user.avatar)
        commit('SET_NICKNAME', user.nickName)
        commit('SET_EMAIL', user.email)
        commit('SET_PHONE_NUMBER', user.phoneNumber)
        commit('SET_SEX', user.sex)
        commit('SET_LOGIN_IP', user.loginIp)
        commit('SET_LOGIN_DATE', user.loginDate)
        commit('SET_REMARK', user.remark)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({commit, state}) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({commit}) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

