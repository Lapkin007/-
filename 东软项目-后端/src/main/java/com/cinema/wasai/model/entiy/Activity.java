package com.cinema.wasai.model.entiy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;


/**
 * 
 * 4.2.8 活动表（t_activity）
 * 
 **/
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class Activity implements Serializable {

	/**主键**/
	private Integer id;

	/**内容**/
	private String content;

	/**参与人数**/
	private Integer number;

	/**开始时间（格式2022-01-02）**/
	private java.util.Date startTime;

	/**结束时间（格式2022-01-02）**/
	private java.util.Date endTime;

	/**添加时间（格式2022-01-14\n11:37:03）**/
	private java.util.Date createTime;

	/**修改时间 (格式2022-01)**/
	private java.util.Date updateTime;



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setNumber(Integer number){
		this.number = number;
	}

	public Integer getNumber(){
		return this.number;
	}

	public void setStartTime(java.util.Date startTime){
		this.startTime = startTime;
	}

	public java.util.Date getStartTime(){
		return this.startTime;
	}

	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}

	public java.util.Date getEndTime(){
		return this.endTime;
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
		return "Activity [id=" + id + ", content=" + content + ", number=" + number + ", startTime=" + startTime + ", endTime=" + endTime + ", createTime=" + createTime + ", updateTime=" + updateTime+ "]";
	}
}
