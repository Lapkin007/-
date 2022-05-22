package com.cinema.wasai.controller;

import com.cinema.wasai.model.entiy.Film;
import com.cinema.wasai.model.entiy.Worker;
import com.cinema.wasai.model.vo.LoginVo;
import com.cinema.wasai.model.vo.WorkerVO;
import com.cinema.wasai.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
@CrossOrigin
@Slf4j
public class WorkerController {
    @Autowired
    WorkerService workerService;

    @GetMapping("")
    public List<Worker> getAllAvaliableWorkers(){
        log.info("FilmController->getAllFilm()");
        return workerService.selectAllAvalibaleWorkers();
    }
    @PostMapping("/login")
    public Map<String,Object> WorkerLogin(@RequestBody WorkerVO vo){
        log.info("WorkerController->WorkerLogin()");
        Map<String,Object> map=new HashMap<>();
        //建立一个worker类来保存搜索条件
        Worker worker1=new Worker();
        worker1.setUsername(vo.getUsername());
        worker1.setPassword(vo.getPassword());
        //查找并存入LIST
        List<Worker> worker2=workerService.selectWorkersByCondition(worker1);
        if(worker2.size()!=0)
        {
            map.put("Worker",worker2.get(0));
            map.put("msg","登陆成功");
            map.put("success","success");
        }
        else{
            map.put("msg","账号或密码错误");
            map.put("success","fail");
        }
        return map;
    }
}
