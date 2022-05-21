import service from "@/utils/request";

//查询活动信息2022/3/31 14:54
export function QueryActivityList(){
    return service({
        url:'/api/activity',
        method:'get',
    })
}
//上传参与活动信息2022/5/19 11:40
export function UploadActivityTakeIn(vo){
    return service({
        url:'/api/registration',
        method:'post',
        data:vo,
    })
}