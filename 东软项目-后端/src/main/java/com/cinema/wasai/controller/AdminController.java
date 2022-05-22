package com.cinema.wasai.controller;

import com.cinema.wasai.model.entiy.Admin;
import com.cinema.wasai.model.entiy.FilmEvaluate;
import com.cinema.wasai.model.vo.AdminVo;
import com.cinema.wasai.model.vo.FilmEvaVo;
import com.cinema.wasai.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
@Slf4j
public class AdminController {
    @Autowired
    AdminService adminService;
    //管理员登录
    @PostMapping("/login")
    public Map<String,Object> AdminLogin(@RequestBody AdminVo vo){
        log.info("AdminController------------>AdminLogin(@RequestBody AdminVo vo)");
        Map<String,Object> map = new HashMap<>();
        Admin admin=new Admin();
        admin.setUsername(vo.getAdmin_id());
        admin.setPassword(vo.getAdmin_pwd());
        List<Admin> sta=adminService.selectAdminsByCondition(admin);
        if(sta.size()!=0)
        {
            //在验证登陆的时候拉取数据,并且传入Map
            Admin admin1=sta.get(0);
            map.put("msg","登录成功");
            map.put("success","success");
            map.put("admin",admin1);
            return map;
        }else{
            map.put("msg","登录失败，返回首页");
            map.put("success","fail");
            return map;
        }
    }
    //获取管理员信息
    @GetMapping("/{id}")
    public Map<String,Object> getAdminById(@PathVariable int id){
        log.info("AdminController------------>getAdminById(@PathVariable int id)");
        Map<String,Object> map=new HashMap<>();
        Admin admin=adminService.selectByPrimaryKey(id);
        map.put("admin",admin);
        return map;
    }
}
