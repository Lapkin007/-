import service from "@/utils/request";

//模糊查询实际2022/3/31 8:37
export function SearchFilm(data){
    return service({
        url:'/api/film/name',
        method:'get',
        data:data
    })
}

//查询电影列表 2022/3/31 9:51

export function QueryFilmList(data){
    return service({
        url:'/api/film',
        method:'get',
        data:data
    })
}

//查询轮播图海报 2022/3/31 10:30

export function ListAllPost(){
    return service({
        url:'/api/poster',
        method:'get',
    })
}

//查询热门榜单 2022/3/31 11:51
export function QueryHotRank(){
    return service({
        url:'/api/film/hot/limit',
        method:'get',
    })
}

//查询电影评论 2022/4/1 8:00
export function QueryFilmComment(fid){
    return service({
        url:'/api/fe/'+fid,
        method:'get',
    })
}

//提交评论 2022/4/1 8:55 测试
export function PostFilmComment(data){
    return service({
        url:'/api/fe',
        method:'post',
        data:data
    })
}

//查询电影排片 2022/4/1 8:41
export function QueryFilmArrangement(fid){
    return service({
        url:'/api/arrangement/film/'+fid,
        method:'get',
    })
}

//查询电影详细排片 2022/4/1 10:21
export function QueryFilmDetailArrangement(aid){
    return service({
        url:'/api/arrangement/id/'+aid,
        method:'get',
    })
}

//查询电影座位情况 2022/4/1 10:29
export function QueryFilmSeat(aid){
    return service({
        url:'/api/arrangement/film/getSeats/'+aid,
        method:'get',
    })
}
//提交评论 2022/4/1 8:55 测试
export function PostToCart(vo){
    return service({
        url:'/api/cart',
        method:'post',
        data:vo,
    })
}