package com.cinema.wasai.controller;

import com.cinema.wasai.model.entiy.*;
import com.cinema.wasai.model.vo.OrderExceptionVo;
import com.cinema.wasai.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @GetMapping("")
    public List<OrderException> GetAllOe(){
        log.info("OrderExceptionController->OrderExceptionVo");
        List<OrderException> oelist=orderExceptionService.selectAllOrderExceptions();
        return oelist;
    }
}
