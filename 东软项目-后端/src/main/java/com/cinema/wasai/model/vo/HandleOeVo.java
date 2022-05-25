package com.cinema.wasai.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleOeVo implements Serializable {
    public Integer id;
    public Integer status;
    public String result;
}
