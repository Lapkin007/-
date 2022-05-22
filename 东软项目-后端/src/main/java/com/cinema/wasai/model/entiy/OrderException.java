package com.cinema.wasai.model.entiy;
import java.io.Serializable;
import java.util.*;


/**
 * 
 * 4.2.14 异常订单表（t_order_exception）
 * 
 **/
@SuppressWarnings("serial")
public class OrderException implements Serializable {

	/**主键**/
	private Integer id;

	/**订单主键（关联t_order表）**/
	private Integer oid;

	/**异常原因**/
	private String reason;

	/**状态 0-待处理；1-已处理**/
	private Integer status;

	/**处理结果**/
	private String result;

	/**申报人**/
	private String reviewer;

	/**结束时间**/
	private java.util.Date endAt;

	/**添加时间（格式2022-01-14\n11:37:03）**/
	private java.util.Date createTime;

	/**更新时间**/
	private java.util.Date updateTime;



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setOid(Integer oid){
		this.oid = oid;
	}

	public Integer getOid(){
		return this.oid;
	}

	public void setReason(String reason){
		this.reason = reason;
	}

	public String getReason(){
		return this.reason;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return this.result;
	}

	public void setReviewer(String reviewer){
		this.reviewer = reviewer;
	}

	public String getReviewer(){
		return this.reviewer;
	}

	public void setEndAt(java.util.Date endAt){
		this.endAt = endAt;
	}

	public java.util.Date getEndAt(){
		return this.endAt;
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
		return "OrderException [id=" + id + ", oid=" + oid + ", reason=" + reason + ", status=" + status + ", result=" + result + ", reviewer=" + reviewer + ", endAt=" + endAt + ", createTime=" + createTime + ", updateTime=" + updateTime+ "]";
	}
}
