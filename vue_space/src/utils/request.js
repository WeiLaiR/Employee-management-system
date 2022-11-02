import axios from 'axios'
import ElementUI from "element-ui";
import router from "@/router";

const request = axios.create({
    baseURL: 'http://localhost:8333',
    timeout: 5000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    let token = localStorage.getItem("token") ? localStorage.getItem("token") : null;
    if (token != null){
        config.headers['token'] = token
    }
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;

        console.log('res======================================' + res)

        

        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }


        if (res.state === "AuthError") {
            ElementUI.Message({
                type: 'error',
                message: res.message
            })
            localStorage.removeItem("token")
            localStorage.removeItem("authority")
            router.push("/login")
        }else if (res.state === "Error") {
            ElementUI.Message({
                type: 'error',
                message: res.message
            })
        }else if (res.state === "401") {
            ElementUI.Message({
                type: 'error',
                message: res.message
            })
            router.push("/401")
        }

        


        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request
