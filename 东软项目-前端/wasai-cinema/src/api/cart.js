import service from "@/utils/request";

//查询用户的购物车信息2022/5/19 11:13
export function QueryCart(uid){
    return service({
        url:'/api/cart/'+uid,
        method:'get',
    })
}

//删除购物车接口2022/3/30 11:13
export function DeleteCart(vo){
    return service({
        url:'/api/cart/',
        method:'delete',
        data:vo,
    })
}