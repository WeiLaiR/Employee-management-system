import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/gloable.css'

Vue.config.productionTip = false

Vue.use(ElementUI,{size: "mini"})
Vue.use(VueAxios,axios)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
