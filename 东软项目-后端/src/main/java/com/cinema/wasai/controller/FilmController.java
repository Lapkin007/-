package com.cinema.wasai.controller;

import com.cinema.wasai.config.MyConstants;
import com.cinema.wasai.model.entiy.Film;
import com.cinema.wasai.model.entiy.FilmEvaluate;
import com.cinema.wasai.model.entiy.Poster;
import com.cinema.wasai.model.entiy.Worker;
import com.cinema.wasai.service.FilmEvaluateService;
import com.cinema.wasai.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/film")
@CrossOrigin
public class FilmController {

    @Autowired
    FilmService filmService;
    @Autowired
    FilmEvaluateService filmEvaluateService;
    //查询所有电影
    @GetMapping("")
    public List<Film> getAllFilm(){
        log.info("FilmController->getAllFilm()");
        List<Film> wk=filmService.selectAllFilms();
        for(Film fl:wk)
        {
            fl.setCover(MyConstants.MY_URL+fl.getCover());
        }
        return wk;
    }
    //查询热门榜单
    @GetMapping("/hot/limit")
    public List<Film> getHotRank() {
        log.info("FilmController->getHotRank()");
        List<Film> wk=filmService.selectAllHotRanks(10);
        for(Film fl:wk)
        {
            fl.setCover(MyConstants.MY_URL+fl.getCover());
        }
        return wk;
    }

    //头部模糊查询
    @GetMapping("/name")
    public List<Film> HeaderSearch(){
        log.info("FilmController->HeaderSearch()");
        List<Film> fl=filmService.selectAllFilms();
        List<Film> nfl=new ArrayList<>();
        for(int i=0;i<fl.size();i++)
        {
            if(fl.get(i).getStatus()==1)
            {
                nfl.add(fl.get(i));
            }
        }
        return nfl;
    }
    //新增电影
    @PostMapping("")
    public Map<String,Object> AddFilm(@RequestBody Film vo){
        Map<String,Object> map=new HashMap<>();
        //设置一个Film的类,用来存放要插入的film信息
        Film film=new Film();
        film.setCover("/images123/error.gif");
        film.setIsDelete("0");
        film.setDuration(vo.getDuration());
        film.setCreateTime(new Date());
        film.setReleaseTime(vo.getReleaseTime());
        film.setHot(0);
        film.setName(vo.getName());
        film.setRegion(vo.getRegion());
        film.setType(vo.getType());
        film.setStatus(0);
        film.setUpdateTime(new Date());
        film.setIntroduction(vo.getIntroduction());
        int value=filmService.insert(film);
        if(value==1)
        {
            map.put("msg","添加成功");
            map.put("success","success");
        }else{
            map.put("msg","添加失败");
            map.put("success","fail");
        }
        return map;
    }
    //删除一个电影
    @DeleteMapping("")
    public Map<String,Object> deleteOneFilm(@RequestParam Integer id)
    {
        Map<String,Object> map=new HashMap<>();
        int value=filmService.deleteByPrimaryKey(id);
        if(value==1)
        {
            map.put("msg","添加成功");
            map.put("success","success");
        }else{
            map.put("msg","添加失败");
            map.put("success","fail");
        }
        return map;
    }
    //修改一个电影
    @PostMapping("/modify")
    public Map<String,Object> modify(@RequestBody Film film) {
        log.info("WorkerController---------------->modify()");
        //查询（通过Id查询当前电影）
        Film temp = filmService.selectByPrimaryKey(film.getId());
        //修改
        int value=filmService.updateByPrimaryKeySelective(film);//通过主键修改其他非空的字段
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
