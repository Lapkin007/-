package com.cinema.wasai.controller;

import com.cinema.wasai.model.entiy.WorkerEvaluate;
import com.cinema.wasai.model.vo.LoginVo;
import com.cinema.wasai.model.vo.WorkerEvaVO;
import com.cinema.wasai.service.WorkerEvaluateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/we")
@CrossOrigin
@Slf4j
public class WorkerEvaluateController {
    @Autowired
    WorkerEvaluateService workerEvaluateService;
    @GetMapping("")
    public List<WorkerEvaluate> getallcomment(){
        return workerEvaluateService.selectAllWorkerEvaluates();
    }
    @PostMapping("")
    public Map<String,Object> AddWorkerEva(@RequestBody WorkerEvaVO vo){
        log.info("WorkerEvaluateController------------>AddWorkerEva(@RequestBody LoginVo vo)");
        Map<String,Object> map = new HashMap<>();
        WorkerEvaluate eva=new WorkerEvaluate();
        eva.setContent(vo.getContent());
        eva.setCreateTime(vo.getCreateTime());
        eva.setUid(vo.getUid());
        eva.setWid(vo.getWid());
        eva.setType(vo.getType());
        eva.setUpdateTime(vo.getUpdateTime());
        int sta=workerEvaluateService.insert(eva);
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
