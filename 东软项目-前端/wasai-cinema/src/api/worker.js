import service from "@/utils/request";

//客服登录 2022/5/22 16:37
export function workerLogin(vo){
    return service({
        url:'/api/worker/login',
        method:'post',
        data:vo,
    })
}

//添加客服 2022/5/23 22:59
export function workerAdd(vo){
    return service({
        url:'/api/worker',
        method:'put',
        data:vo,
    })
}

//查询所有客服 2022/5/23 22:59
export function workerAll(){
    return service({
        url:'/api/worker/all',
        method:'get',
       
    })
}
//删除一个用户
export function DeleteOneWorker(id) { 
    return service({
        url: '/api/worker/'+id,
        method: 'delete',
        
    })
}
//修改员工信息
//修改信息
export function ModifyStaff(data) { //id自动
    return service({
        url: '/api/worker/modify',
        method: 'post',
		data:data
    })
}