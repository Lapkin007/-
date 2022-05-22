import service from "@/utils/request";

//客服登录 2022/5/22 16:37
export function workerLogin(vo){
    return service({
        url:'/api/worker/login',
        method:'post',
        data:vo,
    })
}