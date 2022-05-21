package com.cinema.wasai.mapper;

import com.cinema.wasai.model.entiy.Seat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Map;
import java.util.List;

/**
 * 
 * SeatMapper数据库操作接口类
 * 
 **/
@Mapper
public interface SeatMapper{

	/**
	 *
	 * 查询（根据AID查询）
	 *
	 **/
	List<Seat>  selectByAid ( @Param("aid") Integer aid );
	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Seat  selectByPrimaryKey ( @Param("id") Integer id );
	/**
	 * 
	 * 查询（获得数据表中的数量）
	 * 
	 **/
	public int selectSeatCount();

	/**
	 * 
	 * 查询（根据其他条件查询需要的结果）
	 * 
	 **/
	public int selectSeatsCountByCondition(Map<String,Object> map);

	/**
	 * 
	 * 查询（通过Map条件进行分页查询）
	 * 
	 **/
	public List<Seat> selectSeatsPerPageByCondition(Map<String,Object> map);

	/**
	 * 
	 * 查询（通过Map条件，获得所有符合的信息）
	 * 
	 **/
	public List<Seat> selectSeatsCondition(Map<String,Object> map);

	/**
	 * 
	 * 查询全部信息
	 * 
	 **/
	public List<Seat>  selectAllSeats ( );

	/**
	 * 
	 * 查询（根据实体类的条件查询）
	 * 
	 **/
	public List<Seat> selectSeatsByCondition (Seat record);

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
	public int deleteByCondition( Seat record );

	/**
	 * 
	 * 添加
	 * 
	 **/
	public int insert( Seat record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	public int insertSelective( Seat record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	public int updateByPrimaryKeySelective( Seat record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	public int updateByPrimaryKey ( Seat record );

}