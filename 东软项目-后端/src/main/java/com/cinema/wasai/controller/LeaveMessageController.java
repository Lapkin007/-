package com.cinema.wasai.controller;

import com.cinema.wasai.model.entiy.LeavingMessage;
import com.cinema.wasai.model.entiy.User;
import com.cinema.wasai.model.entiy.WorkerEvaluate;
import com.cinema.wasai.model.vo.LeaveMsgReplyVo;
import com.cinema.wasai.model.vo.LeaveMsgVo;
import com.cinema.wasai.model.vo.WorkerEvaVO;
import com.cinema.wasai.service.LeavingMessageService;
import com.cinema.wasai.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/lm")
@CrossOrigin
@Slf4j
public class LeaveMessageController {
    @Autowired
    LeavingMessageService leavingMessageService;
    @Autowired
    UserService userService;
//    把user注入进留言表的USER里面去
    @GetMapping("")
    public List<LeavingMessage> getAllLeaveingMessageAndUser(){
        List<LeavingMessage> CommentList;
        CommentList=leavingMessageService.selectAllLeavingMessagesAndUser();
        for(int i=0;i<CommentList.size();i++){
            User user=userService.selectByPrimaryKey(CommentList.get(i).getUid());
            CommentList.get(i).setUser(user);
        }
        return CommentList;
    }
    @PostMapping("")
    public Map<String,Object> AddWorkerEva(@RequestBody LeaveMsgVo vo){
        log.info("WorkerEvaluateController------------>AddWorkerEva(@RequestBody LoginVo vo)");
        Map<String,Object> map = new HashMap<>();
        LeavingMessage lm=new LeavingMessage();
        lm.setContent(vo.getContent());
        lm.setCreateTime(vo.getCreateTime());
        lm.setUid(vo.getUid());
        lm.setUpdateTime(vo.getUpdateTime());
        lm.setReply(vo.getReply());
        int sta=leavingMessageService.insert(lm);
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
    @PutMapping("")
    public int AddReply(@RequestBody LeaveMsgReplyVo vo)
    {
        LeavingMessage lm=new LeavingMessage();
        lm.setId(vo.getId());
        lm.setUpdateTime(new Date());
        lm.setReply(vo.getReply());
        int value=leavingMessageService.updateByPrimaryKeySelective(lm);
        return value;
    }
}
