import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    //Mock请求地址
    api_url:"https://www.fastmock.site/mock/50d40bcdfaeaf6c6fafe97c881ce4b50/api",
    //检测是否登录
    islogin:false,
    //用户设置
    now_user:{
      user_name:"ice",
      
    },
    navnum:0,
    //控制Header的点击颜色
    color1:{color:"#5b5d63"},color2:{color:"#5b5d63"},color3:{color:"#5b5d63"},color4:{color:"#5b5d63"},
    //选中的电影，重要设置，用来检测最后一个点的电影
    SelectFilm:{film_name:"狮子王" ,film_img:require("../assets/filmImg/狮子王.jpg")
    ,film_type:"喜剧",film_region:"美国",film_date:"2020-08-21",film_length:"126分钟",film_rate:4.5},

    activeTab:"first"
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
