import service from "@/utils/request";

//查询管理员账号    2022/5/22 10:31
export function QueryAdminAccount(data){
    return service({
        url:'/api/admin/login',
        method:'post',
        data:data
    })
}

//查询管理员的信息   2022/5/22 11:24
export function QueryAdminInfo(id){
    return service({
        url:'/api/admin/'+id,
        method:'get',
    })
}