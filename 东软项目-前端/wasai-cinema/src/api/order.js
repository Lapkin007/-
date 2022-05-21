import service from "@/utils/request";

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