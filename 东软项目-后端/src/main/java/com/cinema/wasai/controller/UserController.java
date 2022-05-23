package com.cinema.wasai.controller;

import com.cinema.wasai.config.MyConstants;
import com.cinema.wasai.model.entiy.User;
import com.cinema.wasai.model.vo.EmailVo;
import com.cinema.wasai.model.vo.LoginVo;
import com.cinema.wasai.model.vo.UserIdVo;
import com.cinema.wasai.service.UserService;
import com.cinema.wasai.util.JwtTokenUtil;
import com.cinema.wasai.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;
    //验证用户名是否存在
    @PostMapping("/isExist")
    public Map<String,String> isExist(@RequestBody EmailVo vo){
        log.info("UserController---------------->isExist(EmailVo vo)");
        Map<String,String> map = new HashMap<>();
        User user = new User();
        user.setUsername(vo.getEmail());//只放置登录名
        List<User> users = userService.selectUsersByCondition(user);
        if(users==null || users.size()==0){
            //没有找到，就可以注册
            map.put("msg","用户名不存在，可以注册");
            map.put("success","success");
        }else{
            map.put("msg","用户名存在，不可以注册");
            map.put("success","fail");
        }
        return  map;

    }

    //注册功能
    @PostMapping("/register")
    public Map<String,Object> register(@RequestBody LoginVo vo){
        log.info("UserController---------------->register(@RequestBody LoginVo vo)");
        Map<String,Object> map = new HashMap<>();
        String token = vo.getToken();
        //获得token中加密的验证码
        String code = JwtTokenUtil.getUserName(token);
        if(code.equalsIgnoreCase(vo.getEmailCode())){
            User user = new User();
            user.setUsername(vo.getUsername());
            user.setPassword(vo.getPassword());
            user.setStatus(0);
            user.setErrorNum(0);
            user.setEmail(vo.getEmail());
            user.setAvatar("https://s1.ax1x.com/2022/04/01/qhEn78.jpg");//默认头像
            user.setNickname("User");//默认昵称
            int insert = userService.insert(user);
            if(insert==1){
                map.put("msg","注册成功");
            }else{
                map.put("msg","注册失败");
            }
        }else{
            map.put("msg","验证码错误");
        }

        return  map;
    }

    //登录功能
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody LoginVo vo){
        log.info("UserController---------------->login(@RequestBody LoginVo vo)");
        Map<String,Object> map = new HashMap<>();
        //获得token中加密的验证码
        User user = new User();
        user.setUsername(vo.getUsername());
        List<User> users  = userService.selectUsersByCondition(user);
        if(users==null  || users.size()==0){
            map.put("msg","用户名或密码错误");
            map.put("success","fail");
        }else{
            User result = users.get(0);
            //用户是否已经禁用，
            if(result.getStatus()==1){
                map.put("msg","该用户已经被禁用，请联系管理员");
                map.put("success","fail");
            }else{
                //加密密码，判断密码是否匹配
                String newPass = MD5Util.md5To32String(vo.getPassword(),result.getSalt(),result.getSaltIndex());
                if(newPass.equalsIgnoreCase(result.getPassword())){
                    //登录成功
                    result.setErrorNum(0);
                    result.setStatus(0);
                    map.put("msg","登录成功");
                    map.put("success","success");
                    List<String> list = new ArrayList<>();
                    list.add("user");
                    String token=JwtTokenUtil.createToken(result.getUsername(),list,JwtTokenUtil.EXPIRATION_TIME);
                    map.put("token",token);
                    map.put("user",result);
                }else{
                    //密码加密后，与数据库中存储的不一致
                    result.setErrorNum(result.getErrorNum()+1);
                    map.put("msg","用户名或密码错误");
                    map.put("success","fail");
                    if(result.getErrorNum()>=5){
                        //锁定账户
                        result.setStatus(1);
                        map.put("msg","用户输入密码连续5次错误，该账户被禁用，请联系管理员");
                        map.put("success","fail");
                    }
                }
                userService.updateByPrimaryKey(result);
            }
        }

        return  map;
    }

    //个人信息功能
    @PostMapping("/id")
    public User getUserId(@RequestBody UserIdVo vo){
        int id=vo.getId();
        log.info("UserController---------------->getUserId(@RequestBody UserIdVo vo)");
        User user = userService.selectByPrimaryKey(id);
        user.setAvatar(MyConstants.MY_URL+user.getAvatar());
        //user.setAvatar(MyConstants.MY_URL+user.getAvatar());
        return  user;
    }

    @Value("${zsp.poster.upload}")
    String upload;
    //文件上传要分开处理
    @PostMapping("/upload")
    public Map<String,String> uploadFile(MultipartFile file) throws IOException {
        log.info("UserController---------------->uploadFile()");
        File path = new File(upload);
        Map<String,String> map = new HashMap<>();

        if(!path.exists()){
            path.mkdirs();//文件夹不存在，创建文件夹
        }
        String originalFilename = file.getOriginalFilename();//获得原始的文件名 "a.jpg"
        int index=originalFilename.lastIndexOf(".");
        String newName = originalFilename.substring(0,index)+"_"+ UUID.randomUUID().toString()+originalFilename.substring(index);
        //保存文件
        try {
            file.transferTo(new File(upload+newName));//不出现异常，就是文件上传成功
            map.put("fileName","/images123/"+newName );//返回新的文件名（新增的）
        } catch (IOException e) {
            map.put("fileName","error");
            e.printStackTrace();//文件上传失败
            throw e;
        }
        return map;
    }
    //修改个人信息
    @PostMapping("/modify")
    public Map<String,Object> modify(@RequestBody User user) {
        log.info("UserController---------------->modify()");
        //查询（通过Id查询当前用户）
        User temp = userService.selectByPrimaryKey(user.getId());
        //把原始的密码进行加密
        String newPass = MD5Util.md5To32String(user.getPassword(),temp.getSalt(),temp.getSaltIndex());
        user.setPassword(newPass);//加密后的密码
        System.out.println(user);
        //修改
        int value=userService.updateByPrimaryKeySelective(user);//通过主键修改其他非空的字段
        Map<String,Object> map = new HashMap<>();
       if(value==1)
       {map.put("token","oook");
           map.put("msg","更新成功");
           map.put("success","success");
       }else{
        map.put("token","nook");
        map.put("msg","更新成功");
        map.put("success","fail");
       }
        return map;
    }

}

