package com.cinema.wasai.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmEvaVo implements Serializable {
    public int fid;
    public int uid;
    public int star;
    public String comment;
    public Date create_time;
    public Date update_time;
}
