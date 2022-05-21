import request from "axios"
import config from "@/config"

const token=localStorage.getItem("token");
const service=request.create({
    baseURL:config.API_URL,
    headers:{"Authorization":token,
             "code":localStorage.getItem('code')}
});

service.interceptors.response.use(
    response=>{
        const res=response.data;
        return res
    },

    error=>{
        console.log('err'+error);
        return Promise.reject(error)
    }
);

export default service