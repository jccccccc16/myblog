import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
// 配置全局配置
export default new Vuex.Store({
  // 配置全局配置
  state: {
    token:'',
    userInfo:JSON.parse(sessionStorage.getItem('userInfo'))
  },

  mutations: {
    // 相当于setter
    SET_TOKEN:(state,token)=>{
      state.token = token
      localStorage.setItem("token",token); // 存储到浏览器的localStorage中
    },
    SET_USERINFO:(state,userInfo)=>{
      state.userInfo = userInfo
      sessionStorage.setItem("userInfo",JSON.stringify(userInfo)); // 存储到浏览器的localStorage中
    },
    REMOVE_INFO:(state)=>{
      state.token = ''
      state.userInfo = {}
      localStorage.setItem("token",'')
      sessionStorage.setItem("userInfo",JSON.stringify(''))

    }
  },
  getters: {
    getUser: state => {
      return state.userInfo
    }
  },
  actions: {
  },
  modules: {
  }
})
