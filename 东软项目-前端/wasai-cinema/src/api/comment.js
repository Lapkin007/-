import service from "@/utils/request";

//查询评论员工信息2022/4/2 14:13
export function QueryWorker(data){
    return service({
        url:'/api/worker',
        method:'get',
        data:data
    })
}


//查询影院评论信息2022/4/2 14:30
export function QueryCinemeComment(){
    return service({
        url:'/api/lm',
        method:'get',
    })
}

//查询员工-我的评价信息2022/4/2 14:30
export function QueryWorkerMycomment(){
    return service({
        url:'/api/we',
        method:'get',
    })
}

//上传我的评价信息2022/5/17 21:16
export function UploadWorkerMycomment(data){
    return service({
        url:'/api/we',
        method:'post',
        data:data
    })
}

//上传影院留言2022/5/17 21:16
export function UploadLeaveMessage(data){
    return service({
        url:'/api/lm',
        method:'post',
        data:data
    })
}

//上传影院留言回复2022/5/17 21:16
export function UploadLeaveMessageReply(data){
    return service({
        url:'/api/lm',
        method:'put',
        data:data
    })
}