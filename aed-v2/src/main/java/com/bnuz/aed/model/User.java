package com.bnuz.aed.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;

/**
 * @Description  
 * @Author  Leia Liang
 * @Date 2020-09-20 
 */

@Entity
@Table ( name = "User" )
public class User implements Serializable {

	private static final long serialVersionUID =  4916090084790425613L;

	/**
	 * 用户ID
	 */
	@Id
   	@Column(name = "user_id" )
	private Long userId;

	/**
	 * 用户名
	 */
   	@Column(name = "user_name" )
	private String userName;

	/**
	 * 微信openid
	 */
   	@Column(name = "wx_openid" )
	private String wxOpenid;

	/**
	 * 手机号码
	 */
   	@Column(name = "phonenumber" )
	private String phonenumber;

	/**
	 * 电子邮箱
	 */
   	@Column(name = "email" )
	private String email;

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWxOpenid() {
		return this.wxOpenid;
	}

	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}

	public String getPhonenumber() {
		return this.phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "TpApiConfig{" +
				"userId='" + userId + '\'' +
				"userName='" + userName + '\'' +
				"wxOpenid='" + wxOpenid + '\'' +
				"phonenumber='" + phonenumber + '\'' +
				"email='" + email + '\'' +
				'}';
	}

}
