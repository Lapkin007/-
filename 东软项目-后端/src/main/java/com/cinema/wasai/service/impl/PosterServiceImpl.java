package com.cinema.wasai.service.impl;

import com.cinema.wasai.mapper.PosterMapper;
import com.cinema.wasai.model.entiy.Poster;
import com.cinema.wasai.model.entiy.Seat;
import com.cinema.wasai.service.PosterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@Transactional
public class PosterServiceImpl implements PosterService {
    @Autowired
    PosterMapper posterMapper;
    //轮播图海报
    @Override
    public List<Poster> getAllPoster() {
        return posterMapper.getAllPoster();
    }

    @Override
    public int insertPoster(Poster poster) {
        log.info("PosterServiceImpl--------------------------------------->insertPoster(Poster poster)");
        return posterMapper.insertPoster(poster);
    }

    @Override
    public List<Poster> selectAllPosters() {
        return posterMapper.selectAllPosters();
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        log.info("主键ID删除Seat信息:deleteByPrimaryKey() ");
        return posterMapper.deleteByPrimaryKey(id);
    }


    @Override
    //修改 （匹配有值的字段）
    public int updateByPrimaryKeySelective(Poster record){
        log.info("修改（匹配有值的字段）Seat信息:updateByPrimaryKeySelective() ");
        return posterMapper.updateByPrimaryKeySelective(record);
    }

}
