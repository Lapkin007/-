package com.cinema.wasai.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveMsgReplyVo implements Serializable {
    public int id;
    public String reply;
}
