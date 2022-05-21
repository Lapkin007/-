package com.cinema.wasai.mapper;

import com.cinema.wasai.model.entiy.Film;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Map;
import java.util.List;

/**
 * 
 * FilmMapper数据库操作接口类
 * 
 **/
@Mapper
public interface FilmMapper{

	/**
	 *
	 * 查询热门榜单
	 *
	 **/
	public List<Film>  selectAllHotRanks (@Param("hot") Integer hot);
	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Film  selectByPrimaryKey ( @Param("id") Integer id );
	/**
	 * 
	 * 查询（获得数据表中的数量）
	 * 
	 **/
	public int selectFilmCount();

	/**
	 * 
	 * 查询（根据其他条件查询需要的结果）
	 * 
	 **/
	public int selectFilmsCountByCondition(Map<String,Object> map);

	/**
	 * 
	 * 查询（通过Map条件进行分页查询）
	 * 
	 **/
	public List<Film> selectFilmsPerPageByCondition(Map<String,Object> map);

	/**
	 * 
	 * 查询（通过Map条件，获得所有符合的信息）
	 * 
	 **/
	public List<Film> selectFilmsCondition(Map<String,Object> map);

	/**
	 * 
	 * 查询全部信息
	 * 
	 **/
	public List<Film>  selectAllFilms ( );

	/**
	 * 
	 * 查询（根据实体类的条件查询）
	 * 
	 **/
	public List<Film> selectFilmsByCondition (Film record);

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	public int deleteByPrimaryKey ( @Param("id") Integer id );

	/**
	 * 
	 * 删除（条件删除）
	 * 
	 **/
	public int deleteByCondition( Film record );

	/**
	 * 
	 * 添加
	 * 
	 **/
	public int insert( Film record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	public int insertSelective( Film record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	public int updateByPrimaryKeySelective( Film record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	public int updateByPrimaryKey ( Film record );

}