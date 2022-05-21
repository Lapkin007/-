package com.cinema.wasai.mapper;

import com.cinema.wasai.model.entiy.FilmEvaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Map;
import java.util.List;

/**
 * 
 * FilmEvaluateMapper数据库操作接口类
 * 
 **/
@Mapper
public interface FilmEvaluateMapper{
	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	FilmEvaluate  selectByPrimaryKey ( @Param("id") Integer id );
	/**
	 *
	 * 查询（根据fid查询）
	 *
	 **/
	public List<FilmEvaluate>  selectByFid( @Param("fid") Integer fid );
	/**
	 * 
	 * 查询（获得数据表中的数量）
	 * 
	 **/
	public int selectFilmEvaluateCount();

	/**
	 * 
	 * 查询（根据其他条件查询需要的结果）
	 * 
	 **/
	public int selectFilmEvaluatesCountByCondition(Map<String,Object> map);

	/**
	 * 
	 * 查询（通过Map条件进行分页查询）
	 * 
	 **/
	public List<FilmEvaluate> selectFilmEvaluatesPerPageByCondition(Map<String,Object> map);

	/**
	 * 
	 * 查询（通过Map条件，获得所有符合的信息）
	 * 
	 **/
	public List<FilmEvaluate> selectFilmEvaluatesCondition(Map<String,Object> map);

	/**
	 * 
	 * 查询全部信息
	 * 
	 **/
	public List<FilmEvaluate>  selectAllFilmEvaluates ( );

	/**
	 * 
	 * 查询（根据实体类的条件查询）
	 * 
	 **/
	public List<FilmEvaluate> selectFilmEvaluatesByCondition (FilmEvaluate record);

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
	public int deleteByCondition( FilmEvaluate record );

	/**
	 * 
	 * 添加
	 * 
	 **/
	public int insert( FilmEvaluate record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	public int insertSelective( FilmEvaluate record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	public int updateByPrimaryKeySelective( FilmEvaluate record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	public int updateByPrimaryKey ( FilmEvaluate record );

}