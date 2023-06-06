import Vue from 'vue'
import Vuex from 'vuex'
// import * as getters from './getters.js'

Vue.use(Vuex)

/** 状态定义
 * ip地址jiu
 */
export const state = {
  loading: false,
  themeObj: {
    // right_img: 'static/img/long.png',
    center_smailimg: 'static/img/libcdut.jpeg',
  },//主题
  keywords: '',//关键词
  errorImg: 'this.onerror=null;this.src="' + require('../../static/img/tou.jpg') + '"',
  baseURL: 'http://localhost:7777/',
}

export default new Vuex.Store({
  state,
})
