package com.cinema.wasai.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartVo implements Serializable {


    /**用户主键（关联t_user表）**/
    public Integer uid;

    /**排片id（关联t_arrangement）**/
    public Integer aid;

    /**电话**/
    public String phone;

    /**座位**/
    public List<Integer> seats;

    /**金额**/
    public java.math.BigDecimal price;

}
