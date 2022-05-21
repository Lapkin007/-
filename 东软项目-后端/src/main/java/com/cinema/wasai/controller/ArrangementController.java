package com.cinema.wasai.controller;

import com.cinema.wasai.model.entiy.*;
import com.cinema.wasai.service.ArrangementService;
import com.cinema.wasai.service.FilmService;
import com.cinema.wasai.service.SeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/arrangement")
@CrossOrigin
@Slf4j
public class ArrangementController {
    @Autowired
    ArrangementService arrangementService;
    @Autowired
    SeatService seatService;
    @Autowired
    FilmService filmService;
    @GetMapping("/film/{fid}")
    public List<Arrangement> getArrangementByFid(@PathVariable int fid){
        log.info("ArrangementController->getArrangementByFid()");
        //把电影列表放进FilmFeList里
        List<Arrangement> arr= arrangementService.selectByFid(fid);
        //循环的同时把User数据塞进去
//        for(int i=0;i<FilmFeList.size();i++){
//            User user=userService.selectByPrimaryKey(FilmFeList.get(i).getUid());
//            FilmFeList.get(i).setUser(user);
//        }
        return arr;

    }
    @GetMapping("/film/getSeats/{aid}")
    public List<Integer> getSeatsByAid(@PathVariable int aid){
        log.info("ArrangementController->getSeatsByAid()");
        //把电影列表放进FilmFeList里
        List<Seat> SeatArr= seatService.selectByAid(aid);
        List<Integer> array=new ArrayList<>();
        for(int i=0;i<SeatArr.size();i++) {
            array.add(SeatArr.get(i).getSeat());
        }
        //循环的同时把User数据塞进去
//        for(int i=0;i<FilmFeList.size();i++){
//            User user=userService.selectByPrimaryKey(FilmFeList.get(i).getUid());
//            FilmFeList.get(i).setUser(user);
//        }
        return array;

    }
    @GetMapping("/id/{aid}")
    public Map<String,Object> getFilmInfo(@PathVariable int aid){
        log.info("ArrangementController->getFilmInfo(@PathVariable int aid)");
        //查询对应AID的场次并且放入arrangement
        Arrangement arr= arrangementService.selectByPrimaryKey(aid);
        Integer fid=arr.getFid();
        log.info(String.valueOf(fid));
        Film film_info=filmService.selectByPrimaryKey(fid);
        Map<String,Object> map = new HashMap<>();
        map.put("arrangement",arr);
        map.put("film",film_info);

        return map;

    }
}
