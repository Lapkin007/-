package com.cinema.wasai.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderExceptionVo implements Serializable {
    public Integer oid;
    public String reason;
    public String reviewer;

}
