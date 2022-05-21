package com.cinema.wasai.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveMsgVo implements Serializable {
    public Date createTime;
    public Date updateTime;
    public String content;
    public String reply;
    public int uid;
}
