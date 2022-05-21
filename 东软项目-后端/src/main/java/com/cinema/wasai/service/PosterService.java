package com.cinema.wasai.service;


import com.cinema.wasai.model.entiy.Poster;

import java.util.List;

public interface PosterService {
    //获得轮播图
    List<Poster> getAllPoster();
    //添加一个轮播图广告
    int insertPoster(Poster poster);
}
