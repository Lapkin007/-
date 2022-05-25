package com.cinema.wasai.controller;

import com.cinema.wasai.config.MyConstants;
import com.cinema.wasai.model.entiy.*;
import com.cinema.wasai.model.vo.FilmEvaVo;
import com.cinema.wasai.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
@Slf4j
public class OrderController {
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
    //通过UID查询相关订单
    @GetMapping("user/{uid}")
    public List<MyOrder> QueryMyOrder(@PathVariable int uid) {
        List<MyOrder> map_out=new ArrayList<>();
        List<Order> order=orderService.selectOrdersByUid(uid);
        for(int i=0;i<order.size();i++) {
            Arrangement arr = arrangementService.selectByPrimaryKey(order.get(i).getAid());
            Film film = filmService.selectByPrimaryKey(arr.getFid());
            film.setCover(MyConstants.MY_URL+film.getCover());
            User user=userService.selectByPrimaryKey(uid);
            //把前面取到的全部放入MyCart类
            MyOrder myOrder=new MyOrder();
            Order od=new Order();
            od=order.get(i);
            myOrder.setOrder(od);
            myOrder.setArr(arr);
            myOrder.setFilm(film);
            myOrder.setUser(user);
            //把myCart加入数组
            map_out.add(myOrder);
        }
//        for(int i=0;i<cart.size();i++)
//        {
//
//            map_out.get(i).setCart(cart.get(i));
//        }
        return map_out;

    }
    //模拟支付成功和失败
    @PostMapping("/paySuccess")
    public Map<String,Object> PaySimulateSuccess(@RequestBody MyCart vo){
        List<Order> orderlist=orderService.selectAllOrders();
        int value=cartService.deleteByCondition(vo.cart);
        for(int i=0;i<orderlist.size();i++){
            if(orderlist.get(i).getAid().equals(vo.cart.getAid()) && orderlist.get(i).getUid().equals(vo.cart.getUid())
                    && orderlist.get(i).getSeats().equals(vo.cart.getSeats().toString())&&orderlist.get(i).getStatus()==0)
            {
                log.info("检测到一个匹配的order");
                Order ca=new Order();
                //设置主键，用来查询要修改的语句
                ca.setId(orderlist.get(i).getId());
                ca.setStatus(2);
                ca.setPayAt(new Date());
                value=orderService.updateByPrimaryKeySelective(ca);

            }
        }

        Map<String,Object> map=new HashMap<>();
        map.put("msg","支付成功");
        map.put("success","success");
        return map;
    }
    @PostMapping("/payFail")
    public Map<String,Object> PaySimulateFail(@RequestBody MyCart vo){
        List<Order> orderlist=orderService.selectAllOrders();
        int value=cartService.deleteByCondition(vo.cart);
        for(int i=0;i<orderlist.size();i++){
            if(orderlist.get(i).getAid().equals(vo.cart.getAid()) && orderlist.get(i).getUid().equals(vo.cart.getUid())
                    && orderlist.get(i).getSeats().equals(vo.cart.getSeats().toString())&&orderlist.get(i).getStatus()==0)
            {
                log.info("检测到一个匹配的order");
                Order ca=new Order();
                //设置主键，用来查询要修改的语句
                ca.setId(orderlist.get(i).getId());
                ca.setStatus(1);
                value=orderService.updateByPrimaryKeySelective(ca);

            }
        }

        Map<String,Object> map=new HashMap<>();
        map.put("msg","支付失败");
        map.put("success","fail");
        return map;
    }
    //查询所有的订单信息(管理端)
    @GetMapping("")
    public List<MyOrder> GetAllOrder() {
        List<MyOrder> map_out=new ArrayList<>();
        List<Order> order=orderService.selectAllOrders();
        for(int i=0;i<order.size();i++) {
            Arrangement arr = arrangementService.selectByPrimaryKey(order.get(i).getAid());
            Film film = filmService.selectByPrimaryKey(arr.getFid());
            User user=userService.selectByPrimaryKey(order.get(i).getUid());
            //把前面取到的全部放入MyCart类
            MyOrder myOrder=new MyOrder();
            Order od=new Order();
            od=order.get(i);
            myOrder.setOrder(od);
            myOrder.setArr(arr);
            myOrder.setFilm(film);
            myOrder.setUser(user);
            //把myCart加入数组
            map_out.add(myOrder);
        }
//        for(int i=0;i<cart.size();i++)
//        {
//
//            map_out.get(i).setCart(cart.get(i));
//        }
        return map_out;

    }

    //撤销某个订单
    @DeleteMapping("/cancle")
    public Integer DeleteOneCart(@RequestBody MyOrder vo){
        //先删除用户购物车里的内容
        Cart cart=new Cart();
        cart.setAid(vo.order.getAid());
        cart.setUid(vo.order.getUid());
        cart.setSeats(Integer.valueOf(vo.order.getSeats()));
        int value3=cartService.deleteByCondition(cart);
        log.info("删除对应用户购物车成功");
        //然后更改订单列表里的状态为已被撤销 状态码为3
        //把查询到的订单放入order
        Order order=orderService.selectByPrimaryKey(vo.order.getId());
        log.info("检测到一个匹配的order");
        Order ca=new Order();
        //设置主键，用来查询要修改的语句
        ca.setId(order.getId());
        //设置修改的信息
        ca.setStatus(3);
        ca.setUpdateTime(new Date());
        value3=orderService.updateByPrimaryKeySelective(ca);
        //删除的时候同时把座位表里的内容删除
        Seat se=new Seat();
        se.setAid(order.getAid());
        se.setSeat(Integer.valueOf(order.getSeats()));
        int value2=seatService.deleteByCondition(se);
        return value2;
    }
}
