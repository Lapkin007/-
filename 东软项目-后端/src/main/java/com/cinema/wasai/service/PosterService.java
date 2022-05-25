package com.cinema.wasai.service;


import com.cinema.wasai.model.entiy.Poster;
import com.cinema.wasai.model.entiy.Seat;
import com.cinema.wasai.model.entiy.Worker;

import java.util.List;

public interface PosterService {
    //获得轮播图
    List<Poster> getAllPoster();
    //添加一个轮播图广告
    int insertPoster(Poster poster);
    //获得轮播图
    List<Poster> selectAllPosters();

    int deleteByPrimaryKey(Integer id);

    //修改 （匹配有值的字段）
    int updateByPrimaryKeySelective(Poster record);
}
