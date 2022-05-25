package com.cinema.wasai.controller;

import com.cinema.wasai.model.entiy.*;
import com.cinema.wasai.model.vo.HandleOeVo;
import com.cinema.wasai.model.vo.OrderExceptionVo;
import com.cinema.wasai.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/oe")
@CrossOrigin
@Slf4j
public class OrderExceptionController {
    @Autowired
    UserService userService;
    @Autowired
    SeatService seatService;
    @Autowired
    CartService cartService;
    @Autowired
    FilmService filmService;
    @Autowired
    ArrangementService arrangementService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderExceptionService orderExceptionService;
    //提交异常订单
    @PostMapping("")
    public Integer PostOe(@RequestBody OrderExceptionVo vo){
        //把查询到的订单放入order
        Order order=orderService.selectByPrimaryKey(vo.getOid());
        log.info("检测到一个匹配的order");
        //定义一个异常类插入表
        OrderException oe=new OrderException();
        oe.setOid(order.getId());
        oe.setReason(vo.getReason());
        oe.setStatus(0);
        oe.setUpdateTime(new Date());
        oe.setCreateTime(new Date());
        oe.setReviewer(vo.getReviewer());

        int value=orderExceptionService.insert(oe);
        return value;
    }
    //获得所有异常订单
    @GetMapping("")
    public List<OrderException> GetAllOe(){
        log.info("OrderExceptionController->OrderExceptionVo");
        List<OrderException> oelist=orderExceptionService.selectAllOrderExceptions();
        return oelist;
    }
    //处理异常订单
    @PutMapping("")
        public Map<String,Object> HandleOE(@RequestBody HandleOeVo vo)
    {
        Map<String,Object> map=new HashMap<>();
        //创建一个异常类的对象，按主键来更新其中的信息
        OrderException oe=new OrderException();
        oe.setId(vo.getId());
        oe.setStatus(vo.getStatus());
        oe.setResult(vo.getResult());
        int value=orderExceptionService.updateByPrimaryKeySelective(oe);
        if(value==1)
        {
            map.put("msg","处理成功");
            map.put("success","success");
        }else{
            map.put("msg","处理失败");
            map.put("success","fail");
        }
        return map;
    }

}
