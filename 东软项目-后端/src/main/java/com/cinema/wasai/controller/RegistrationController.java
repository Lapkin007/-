package com.cinema.wasai.controller;

import com.cinema.wasai.model.entiy.FilmEvaluate;
import com.cinema.wasai.model.entiy.Registration;
import com.cinema.wasai.model.vo.RegistrationVo;
import com.cinema.wasai.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/registration")
@CrossOrigin
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;
    @PostMapping("")
    public Map<String,Object> AddtoRegistrationList(@RequestBody RegistrationVo vo){
        log.info("RegistrationController------------>AddtoRegistrationList(@RequestBody RegistrationVo vo))");
        Map<String,Object> map = new HashMap<>();
        Registration re=new Registration();
        re.setCreateTime(vo.getCreate_time());
        re.setUid(vo.getUid());
        re.setUpdateTime(vo.getUpdate_time());
        re.setAid(vo.getAid());
        List<Registration> relist=registrationService.selectAllRegistrations();
        for(int i=0;i<relist.size();i++)
        {
            if(vo.uid==relist.get(i).getUid()&&vo.aid==relist.get(i).getAid())
            {
                map.put("msg","已经参加过此活动");
                map.put("success","fail");
                return map;
            }
        }
        int sta=registrationService.insert(re);
        if(sta==1)
        {
            map.put("msg","参加活动成功");
            map.put("success","success");
            return map;
        }else{
            map.put("msg","参加活动失败，请重新尝试");
            map.put("success","fail");
            return map;
        }
    }
}
