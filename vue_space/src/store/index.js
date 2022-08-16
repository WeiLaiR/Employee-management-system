import { createStore } from 'vuex'

// 创建一个新的 store 实例
const store = createStore({
    state () {
        return {
            currentPathName: '',
            empNameValue: ''
        }
    },
    mutations: {
        setPath (state) {
            state.currentPathName = localStorage.getItem("currentPathName")
            state.empNameValue = localStorage.getItem("empName")
        }
    }
})

export default store
