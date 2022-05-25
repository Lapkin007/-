package com.cinema.wasai.controller;

import com.cinema.wasai.config.MyConstants;
import com.cinema.wasai.model.entiy.Film;
import com.cinema.wasai.model.entiy.User;
import com.cinema.wasai.model.entiy.Worker;
import com.cinema.wasai.model.vo.LoginVo;
import com.cinema.wasai.model.vo.WorkerVO;
import com.cinema.wasai.service.WorkerService;
import com.cinema.wasai.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/worker")
@CrossOrigin
@Slf4j
public class WorkerController {
    @Autowired
    WorkerService workerService;
    @GetMapping("/all")
    public List<Worker> getAllWorkers()
    {
        log.info("WorkerController->getAllWorkers()");
        List<Worker> wk=workerService.selectAllWorkers();
        for(Worker worker:wk)
        {
            worker.setAvatar(MyConstants.MY_URL+worker.getAvatar());
        }
        return wk;
    }
    @GetMapping("")
    public List<Worker> getAllAvaliableWorkers(){
        log.info("WorkerController->getAllAvaliableWorkers()");
        List<Worker> wk=workerService.selectAllAvalibaleWorkers();
        for(Worker worker:wk)
        {
            worker.setAvatar(MyConstants.MY_URL+worker.getAvatar());
        }
        return wk;
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
    @PutMapping("")
    public Map<String,Object> WorkerPut(@RequestBody Worker vo){
        Map<String,Object> map=new HashMap<>();
        Worker wk=new Worker();
        wk.setUsername(vo.getUsername());
        wk.setPassword(vo.getPassword());
        wk.setAvatar("https://s1.ax1x.com/2022/04/01/qhE31s.jpg");
        wk.setPhone(vo.getPhone());
        wk.setDepartment(vo.getDepartment());
        wk.setCreateTime(new Date());
        wk.setUpdateTime(new Date());
        wk.setEntry(vo.getEntry());
        wk.setGender(vo.getGender());
        wk.setNickname(vo.getNickname());
        int value=workerService.insert(wk);
        if(value==1)
        {
            map.put("msg","添加成功");
            map.put("success","success");
        }else{
            map.put("token","nook");
            map.put("msg","更新失败");
            map.put("success","fail");
        }
        return map;
    }
    @DeleteMapping("/{id}")
    public Map<String,Object> DeleteOneWorker(@PathVariable Integer id)
    {
        Map<String,Object> map=new HashMap<>();
        int value=workerService.deleteByPrimaryKey(id);
        if(value==1)
        {
            map.put("msg","删除成功");
            map.put("success","success");
        }else{

            map.put("msg","删除失败");
            map.put("success","fail");
        }
        return map;
    }
    @PostMapping("/modify")
    public Map<String,Object> modify(@RequestBody Worker worker) {
        log.info("WorkerController---------------->modify()");
        //查询（通过Id查询当前用户）
        Worker temp = workerService.selectByPrimaryKey(worker.getId());
        //修改
        int value=workerService.updateByPrimaryKeySelective(worker);//通过主键修改其他非空的字段
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
