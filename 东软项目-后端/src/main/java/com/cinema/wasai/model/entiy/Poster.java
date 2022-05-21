package com.cinema.wasai.model.entiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Poster implements Serializable {
    private Integer id;
    private Integer fid;
    private String title;
    private String url;
    private int status;
    private Date createTime;
    private Date updateTime;

}
