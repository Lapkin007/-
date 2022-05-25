package com.cinema.wasai.mapper;

import com.cinema.wasai.model.entiy.Poster;
import com.cinema.wasai.model.entiy.Seat;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PosterMapper {
    @Select("select * from t_poster where status=1")
    List<Poster> getAllPoster();


    List<Poster> selectAllPosters();

    @Insert("insert into t_poster values(null,#{fid},#{title},#{url},#{status},now(),now())")
    int insertPoster(Poster poster);

    //删除方法
    int deleteByPrimaryKey(Integer id);

    //匹配有值的字段
    int updateByPrimaryKeySelective(Poster record);
}
