package com.cinema.wasai.controller;

import com.cinema.wasai.config.MyConstants;
import com.cinema.wasai.model.entiy.*;
import com.cinema.wasai.model.vo.CartVo;
import com.cinema.wasai.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.SearchControls;
import java.util.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin
@Slf4j
public class CartController {
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
    @PostMapping("")
    public Map<String,Object> AddToCart(@RequestBody CartVo vo){
        log.info("CartController------------>AddToCart(@RequestBody CartVo vo)");
        Map<String,Object> map = new HashMap<>();
        Cart cart=new Cart();
        int flag=1;//验证所有请求通过
        for(int i=0;i<vo.getSeats().size();i++) {
            cart.setUid(vo.getUid());
            cart.setAid(vo.getAid());
            cart.setPhone(vo.getPhone());
            List<Integer> num=vo.getSeats();
            cart.setStatus(0);
            cart.setSeats(num.get(i));
            cart.setPrice(vo.getPrice());
            cart.setCreateTime(new Date());
            cart.setUpdateTime(new Date());
            int value=cartService.insert(cart);
            flag=value;
            if(value!=1)
            {
                map.put("msg","选座失败，请重新尝试");
                map.put("success","fail");
                return map;
            }
        }
        //验证是否全部POST成功，如果成功，把座位先加入场次信息,然后同时生成未支付的订单
        if(flag==1)
        {
            //定义一个座位的累seat，把信息输入，还有一个order的类，并且输入
            Seat seat=new Seat();
            Order order=new Order();
            for(int i=0;i<vo.getSeats().size();i++)
            {seat.setAid(vo.getAid());
            List<Integer> num=vo.getSeats();
            seat.setSeat(num.get(i));
            seat.setCreateTime(new Date());
            seat.setUpdateTime(new Date());
            //order部分
                log.info("插入ORDER表部分");
            order.setAid(vo.getAid());
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());
            order.setStatus(0);
            order.setPhone(vo.getPhone());
            order.setPrice(vo.getPrice());
            order.setSeats(String.valueOf(num.get(i)));
            order.setUid(vo.getUid());
                 int value2=orderService.insert(order);
                 int value1=seatService.insert(seat);
                if(value1!=1||value2!=1)
                {
                    map.put("msg","选座或者加入订单失败，请重新尝试");
                    map.put("success","fail");
                    return map;
                }

            }
            //第二重验证
            if(flag==1) {
                map.put("msg", "选座成功");
                map.put("success", "success");
                return map;
            }
            else{map.put("msg","选座失败，请重新尝试");
                map.put("success","fail");
                return map;}
        }else{
            map.put("msg","选座失败，请重新尝试");
            map.put("success","fail");
            return map;
        }


    }
    @GetMapping("/{uid}")
    public List<MyCart> QueryMyCart(@PathVariable int uid) {
        List<MyCart> map_out=new ArrayList<>();
        List<Cart> cart=cartService.selectByUid(uid);
        for(int i=0;i<cart.size();i++) {
            Arrangement arr = arrangementService.selectByPrimaryKey(cart.get(i).getAid());
            Film film = filmService.selectByPrimaryKey(arr.getFid());
            film.setCover(MyConstants.MY_URL+film.getCover());
            //把前面取到的全部放入MyCart类
            MyCart myCart=new MyCart();
            Cart ca=new Cart();
            ca=cart.get(i);
            myCart.setCart(ca);
            myCart.setArr(arr);
            myCart.setFilm(film);
            //把myCart加入数组
            map_out.add(myCart);
        }
//        for(int i=0;i<cart.size();i++)
//        {
//
//            map_out.get(i).setCart(cart.get(i));
//        }
        return map_out;

    }
    @DeleteMapping("")
    public Integer DeleteOneCart(@RequestBody Cart vo){
        int value=cartService.deleteByCondition(vo);
        log.info("删除成功");
        List<Order> orderlist=orderService.selectAllOrders();
        for(int i=0;i<orderlist.size();i++){
            if(orderlist.get(i).getAid().equals(vo.getAid()) && orderlist.get(i).getUid().equals(vo.getUid())
            && orderlist.get(i).getSeats().equals(vo.getSeats().toString())&&orderlist.get(i).getStatus()!=3)
            {
               log.info("检测到一个匹配的order");
               Order ca=new Order();
               //设置主键，用来查询要修改的语句
                ca.setId(orderlist.get(i).getId());
               ca.setStatus(3);
               value=orderService.updateByPrimaryKeySelective(ca);
               //删除的时候同时把座位表里的内容删除
                Seat se=new Seat();
                se.setAid(orderlist.get(i).getAid());
                se.setSeat(vo.getSeats());
               int value2=seatService.deleteByCondition(se);

            }
        }
        return value;
    }

}
