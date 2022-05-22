package com.cinema.wasai.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminVo implements Serializable {
    public String admin_id;
    public String admin_pwd;
}
