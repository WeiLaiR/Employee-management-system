import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/gloable.css'
import request from "@/utils/request";
import store from "./store/index";
import JSEncrypt from 'jsencrypt';
import SHA256 from 'js-sha256'

Vue.config.productionTip = false

Vue.use(ElementUI,{size: "mini"})
Vue.use(VueAxios,axios)
Vue.prototype.$jsEncrypt = JSEncrypt
Vue.prototype.$SHA256 = SHA256
Vue.prototype.request=request

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
