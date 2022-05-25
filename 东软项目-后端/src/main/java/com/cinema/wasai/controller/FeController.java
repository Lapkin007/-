package com.cinema.wasai.controller;


import com.cinema.wasai.config.MyConstants;
import com.cinema.wasai.model.entiy.FilmEvaluate;
import com.cinema.wasai.model.entiy.LeavingMessage;
import com.cinema.wasai.model.entiy.User;
import com.cinema.wasai.model.support.ResponseResult;
import com.cinema.wasai.model.vo.FilmEvaVo;
import com.cinema.wasai.model.vo.LeaveMsgVo;
import com.cinema.wasai.service.FilmEvaluateService;
import com.cinema.wasai.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fe")
@CrossOrigin
@Slf4j
public class FeController {
    @Autowired
    FilmEvaluateService filmEvaluateService;
    @Autowired
    UserService userService;

    @GetMapping("/{fid}")
    public List<FilmEvaluate> getCommentByFid(@PathVariable int fid){
        log.info("FilmController->getAllFilm()");
        //把电影列表放进FilmFeList里
        List<FilmEvaluate> FilmFeList= filmEvaluateService.selectByFid(fid);
        //循环的同时把User数据塞进去
        for(int i=0;i<FilmFeList.size();i++){
            User user=userService.selectByPrimaryKey(FilmFeList.get(i).getUid());
            user.setAvatar(MyConstants.MY_URL+user.getAvatar());
            FilmFeList.get(i).setUser(user);
        }
        return FilmFeList;

    }
//@GetMapping("/{fid}")
//public ResponseResult getCommentByFid(@PathVariable int fid){
//    log.info("FilmController->getAllFilm()");
//    //把电影列表放进FilmFeList里
//    List<FilmEvaluate> FilmFeList= filmEvaluateService.selectByFid(fid);
//    //循环的同时把User数据塞进去
//    for(int i=0;i<FilmFeList.size();i++){
//        User user=userService.selectByPrimaryKey(FilmFeList.get(i).getUid());
//        user.setAvatar(MyConstants.MY_URL+user.getAvatar());
//        FilmFeList.get(i).setUser(user);
//    }
//    return new ResponseResult(MyConstants.MY_URL + FilmFeList.get(0).getUser().getAvatar());
//}
    @PostMapping("")
    public Map<String,Object> AddFilmEva(@RequestBody FilmEvaVo vo){
        log.info("WorkerEvaluateController------------>AddFilmEva(@RequestBody LoginVo vo)");
        Map<String,Object> map = new HashMap<>();
        FilmEvaluate em=new FilmEvaluate();
        em.setComment(vo.getComment());
        em.setCreateTime(vo.getCreate_time());
        em.setUid(vo.getUid());
        em.setUpdateTime(vo.getUpdate_time());
        em.setFid(vo.getFid());
        em.setStar(vo.getStar());
        int sta=filmEvaluateService.insert(em);
        if(sta==1)
        {
            map.put("msg","评论成功");
            map.put("success","success");
            return map;
        }else{
            map.put("msg","评论失败，请重新尝试");
            map.put("success","fail");
            return map;
        }
    }
}
