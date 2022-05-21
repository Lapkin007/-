package com.cinema.wasai.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerEvaVO implements Serializable {
    public Date createTime;
    public Date updateTime;
    public String content;
    public String type;
    public int uid;
    public int wid;

}
