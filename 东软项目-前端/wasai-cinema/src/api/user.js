import service from "@/utils/request";

//登录 2022/3/30 9:21
export function Login(data){
    return service({
        url:'/api/user/login',
        method:'post',
        data:data
    })
}

//注册2022/3/30 10:21
export function Register(data){
    return service({
        url:'/api/user/register',
        method:'post',
        data:data
    })
}

//查询用户信息2022/3/30 11:13
export function UserInfo(data){
    return service({
        url:'/api/user/id',
        method:'post',
        data:data,
    })
}

//模糊查询实际2022/3/31 8:37
export function SearchFilm(data){
    return service({
        url:'/api/film/name',
        method:'get',
        data:data
    })
}
//验证用户名是否存在
export function IsExistUserName(data) {
    return service({
        url: '/api/user/isExist',
        method: 'post',
        data:data
    })
}
//发送邮箱验证码
export function SendEmailCode(data) {
    return service({
        url: '/api/common/email',
        method: 'post',
        data:data
    })
}
