package com.cinema.wasai.model.entiy;
import java.io.Serializable;
import java.util.*;


/**
 * 
 * 4.2.2 管理员表（t_admin）
 * 
 **/
@SuppressWarnings("serial")
public class Admin implements Serializable {

	/**主键**/
	private Integer id;

	/**用户名**/
	private String username;

	/**密码**/
	private String password;

	/**头像（图片id，关联
t_film.id）**/
	private String avatar;

	/**创建时间**/
	private java.util.Date createTime;

	/**更新时间**/
	private java.util.Date updateTime;



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return this.avatar;
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
		return "Admin [id=" + id + ", username=" + username + ", password=" + password + ", avatar=" + avatar + ", createTime=" + createTime + ", updateTime=" + updateTime+ "]";
	}
}
