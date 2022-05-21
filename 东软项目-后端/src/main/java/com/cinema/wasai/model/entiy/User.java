package com.cinema.wasai.model.entiy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;


/**
 * 
 * 4.2.1 用户表
 * 
 **/
@SuppressWarnings("serial")
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	/**'主键'**/
	private Integer id;

	/**'用户名'**/
	private String username;

	/**'密码'**/
	private String password;

	/**'盐'**/
	private String salt;

	/**'盐索引'**/
	private Integer saltIndex;

	/****/
	private String avatar;

	/**'昵称'**/
	private String nickname;

	/**'邮箱'**/
	private String email;

	/**生日（格式2022-01-02）
**/
	private java.util.Date birthday;

	/**'性别（男生；女生）'**/
	private String gender;

	/**'个人简介'**/
	private String info;

	/**'创建时间（格式2022-01-14'**/
	private java.util.Date createTime;

	/**'更新时间（格式2022-01-14'**/
	private java.util.Date updateTime;

	/**'错误次数'**/
	private Integer errorNum;

	/**'状态'**/
	private Integer status;

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

	public void setSalt(String salt){
		this.salt = salt;
	}

	public String getSalt(){
		return this.salt;
	}

	public void setSaltIndex(Integer saltIndex){
		this.saltIndex = saltIndex;
	}

	public Integer getSaltIndex(){
		return this.saltIndex;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return this.avatar;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public String getNickname(){
		return this.nickname;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setBirthday(java.util.Date birthday){
		this.birthday = birthday;
	}

	public java.util.Date getBirthday(){
		return this.birthday;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return this.gender;
	}

	public void setInfo(String info){
		this.info = info;
	}

	public String getInfo(){
		return this.info;
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

	public Integer getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(Integer errorNum) {
		this.errorNum = errorNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", salt=" + salt + ", saltIndex=" + saltIndex + ", avatar=" + avatar + ", nickname=" + nickname + ", email=" + email + ", birthday=" + birthday + ", gender=" + gender + ", info=" + info + ", createTime=" + createTime + ", updateTime=" + updateTime+ "]";
	}
}
