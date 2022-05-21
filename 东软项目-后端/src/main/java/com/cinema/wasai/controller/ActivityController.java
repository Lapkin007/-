package com.cinema.wasai.controller;


import com.cinema.wasai.model.entiy.Activity;
import com.cinema.wasai.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
@CrossOrigin
@Slf4j
public class ActivityController {
    @Autowired
    ActivityService activityService;
    @GetMapping("")
    public List<Activity> getAllActivities(){
        return activityService.selectAllActivitys();
    }
}
