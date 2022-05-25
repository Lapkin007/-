package com.cinema.wasai.controller;

import com.cinema.wasai.config.MyConstants;
import com.cinema.wasai.model.entiy.*;
import com.cinema.wasai.model.support.ResponseResult;
import com.cinema.wasai.service.*;
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
    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
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
        film_info.setCover(MyConstants.MY_URL+ film_info.getCover());
        Map<String,Object> map = new HashMap<>();
        map.put("arrangement",arr);
        map.put("film",film_info);

        return map;

    }
    //查询所有的排场次表和附带的电影信息和座位信息
    @GetMapping("")
    public List< Map<String,Object> > getAllArrWithFilmAndSeat(){
        List< Map<String,Object> > map=new ArrayList<>();
        List<Arrangement> ArrList=arrangementService.selectAllArrangements();
        for(int i=0;i<ArrList.size();i++)
        {
            //建立一个MAP MO
            Map<String,Object> mo=new HashMap<>();
            //新建立一个arr,放入MO
            Arrangement arr=new Arrangement();
            arr=ArrList.get(i);
            mo.put("Arr",arr);
            //新建立一个Film
            Film film=new Film();
            film=filmService.selectByPrimaryKey(arr.getFid());
            film.setCover(MyConstants.MY_URL+film.getCover());
            mo.put("Film",film);
            //新建立一个存放座位的数组
            //建一个包含所有当前场次的座位表的Seat数组
            List<Seat> seatlist=new ArrayList<>();
            seatlist=seatService.selectByAid(arr.getId());
            List<Integer> seats=new ArrayList<>();
            //座位列表循环并且把seat塞入seatlist
            for(int j=0;j<seatlist.size();j++)
            {
                Integer aseat=0;
                aseat=seatlist.get(j).getSeat();
                seats.add(aseat);
            }
            mo.put("Seats",seats);
            map.add(mo);
        }
        return map;
    }
    //修改一个电影
    @PostMapping("/modify")
    public Map<String,Object> modify(@RequestBody Arrangement arr) {
        log.info("ArrangementController---------------->modify()");
        //查询（通过Id查询当前电影）
        Arrangement temp = arrangementService.selectByPrimaryKey(arr.getId());
        //修改
        int value=arrangementService.updateByPrimaryKeySelective(arr);//通过主键修改其他非空的字段
        Map<String,Object> map = new HashMap<>();
        if(value==1)
        {map.put("token","oook");
            map.put("msg","更新成功");
            map.put("success","success");
        }else{
            map.put("token","nook");
            map.put("msg","更新失败");
            map.put("success","fail");
        }
        return map;
    }
    //删除一个场次
    @DeleteMapping("")
    public Map<String,Object> DeleteOneArr(@RequestParam Integer aid)
    {
        Map<String,Object> map=new HashMap<>();
        //先设置各种类制造条件
        Seat sa=new Seat();
        Cart ca=new Cart();
        Order oa=new Order();
        ca.setAid(aid);
        sa.setAid(aid);
        oa.setAid(aid);
        oa.setStatus(3);
        int value=cartService.deleteByCondition(ca);
        //删除座位表里的内容
        int value2=seatService.deleteByCondition(sa);
        //更改订单里所有Aid相关状态
        int value3= orderService.updateByAidSelective(oa);
        int value4= arrangementService.deleteByPrimaryKey(aid);
        if(value==1&value2==1&value3==1)
        {map.put("token","oook");
            map.put("msg","更新成功");
            map.put("success","success");
        }else{
            map.put("token","nook");
            map.put("msg","更新失败");
            map.put("success","fail");
        }
        return map;

    }
    //增加一个场次
    @PutMapping("")
    public Map<String,Object> AddArr(@RequestBody Arrangement vo)
    {
        int value=arrangementService.insert(vo);
        Map<String,Object> map = new HashMap<>();
        if(value==1)
        {map.put("token","oook");
            map.put("msg","更新成功");
            map.put("success","success");
        }else{
            map.put("token","nook");
            map.put("msg","更新失败");
            map.put("success","fail");
        }
        return map;
    }
}
