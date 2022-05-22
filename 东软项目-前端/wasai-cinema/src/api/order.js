import service from "@/utils/request";

//查询所有订单 2022/5/22 11:59
export function QueryAllorder(){
    return service({
        url:'/api/order',
        method:'get',
    })
}
//查询个人订单 2022/5/20 14:52
export function QueryOrderByUid(uid){
    return service({
        url:'/api/order/user/'+uid,
        method:'get',
    })
}
//模拟支付成功 2022/5/20 15:15
export function SimPayFail(data){
    return service({
        url:'/api/order/payFail',
        method:'post',
        data:data,
    })
}
//模拟支付失败 2022/2/20 15:15
export function SimPaySuccess(data){
    return service({
        url:'/api/order/paySuccess',
        method:'post',
        data:data
    })
}
//撤销订单接口 2022/5/22 14:33
export function CancleOneOrder(vo){
    return service({
        url:'/api/order/cancle',
        method:'delete',
        data:vo,
    })
}
//上报异常订单接口 2022/5/22 14:33
export function PostOeOrder(vo){
    return service({
        url:'/api/oe',
        method:'post',
        data:vo,
    })
}
//去的所有异常订单接口 2022/5/22 16:33
export function GetAlloe(){
    return service({
        url:'/api/oe',
        method:'get',
    })
}