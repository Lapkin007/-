package com.cinema.wasai.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//活动深申请表的VO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationVo {
    public Date create_time;
    public Date update_time;
    public int aid;
    public int uid;
}
