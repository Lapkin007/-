package com.cinema.wasai.model.entiy;
import java.io.Serializable;


/**
 * 
 * 4.2.13 订单表（t_order）
 * 
 **/
@SuppressWarnings("serial")
public class Order implements Serializable {

	/**主键**/
	private Integer id;

	/**用户主键（关联t_user表）**/
	private Integer uid;

	/**电话号**/
	private String phone;

	/**排片id（关联t_arrangement）**/
	private Integer aid;

	/**座位**/
	private String seats;

	/**金额**/
	private java.math.BigDecimal price;

	/**状态 0-等待支付；1-支付失败；2-支付成
功；3-已被撤销**/
	private Integer status;

	/**支付时间**/
	private java.util.Date payAt;

	/**添加时间（格式2022-01-14 11:37:03）**/
	private java.util.Date createTime;

	/**更新时间**/
	private java.util.Date updateTime;



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setUid(Integer uid){
		this.uid = uid;
	}

	public Integer getUid(){
		return this.uid;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setAid(Integer aid){
		this.aid = aid;
	}

	public Integer getAid(){
		return this.aid;
	}

	public void setSeats(String seats){
		this.seats = seats;
	}

	public String getSeats() {
		return seats;
	}

	public void setPrice(java.math.BigDecimal price){
		this.price = price;
	}

	public java.math.BigDecimal getPrice(){
		return this.price;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setPayAt(java.util.Date payAt){
		this.payAt = payAt;
	}

	public java.util.Date getPayAt(){
		return this.payAt;
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
		return "Order [id=" + id + ", uid=" + uid + ", phone=" + phone + ", aid=" + aid + ", seats=" + seats + ", price=" + price + ", status=" + status + ", payAt=" + payAt + ", createTime=" + createTime + ", updateTime=" + updateTime+ "]";
	}
}
