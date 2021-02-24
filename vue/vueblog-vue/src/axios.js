import axios from 'axios'
import Element from 'element-ui'
import router from './router'
import store from './store'
axios.defaults.baseURL='http://localhost:8080';

// 前置拦截
axios.interceptors.request.use(config =>{

    return config;
})

// 或为全局的异常处理
// 后置拦截
// 当我们的axios的请求返回了reponse
axios.interceptors.response.use(response =>{
    let res = response.data;
    console.log("data: "+res)
    if(res.code === 200){
        return response;
    }else{
        Element.Message.error(res.msg,{duration:3* 1000});  // 设置3秒消失
        return Promise.reject(response.dat.msg);
    }
},error => {
        if(error.response.data){
            error.message = error.response.data.msg1
        }

        if(error.response.status===401){
            store.commit("REMOVE_INFO")
            router.push("/login")
        }
    Element.Message.error(error.msg,{duration:3* 1000});  // 设置3秒消失
    return Promise.reject(error);

    }

)