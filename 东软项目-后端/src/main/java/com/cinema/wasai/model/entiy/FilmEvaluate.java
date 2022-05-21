package com.cinema.wasai.model.entiy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;


/**
 * 
 * 4.2.12 电影评论表（t_film_evaluate）
 * 
 **/
@SuppressWarnings("serial")
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class FilmEvaluate implements Serializable {

	/**主键**/
	private Integer id;

	/**电影主键（关联t_film表）**/
	private Integer fid;

	/**用户主键（关联t_user表）**/
	private Integer uid;

	/**评分**/
	private Integer star;

	/**评论内容**/
	private String comment;

	/**添加时间（格式2022-01-14\n11:37:03）**/
	private java.util.Date createTime;

	/**更新时间**/
	private java.util.Date updateTime;

    private User user;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setFid(Integer fid){
		this.fid = fid;
	}

	public Integer getFid(){
		return this.fid;
	}

	public void setUid(Integer uid){
		this.uid = uid;
	}

	public Integer getUid(){
		return this.uid;
	}

	public void setStar(Integer star){
		this.star = star;
	}

	public Integer getStar(){
		return this.star;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return this.comment;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

	@Override
	public String toString() {
		return "FilmEvaluate [id=" + id + ", fid=" + fid + ", uid=" + uid + ", star=" + star + ", comment=" + comment + ", createTime=" + createTime + ", updateTime=" + updateTime+ "]";
	}
}
