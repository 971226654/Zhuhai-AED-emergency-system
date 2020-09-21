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
@Table ( name = "Manager" )
public class Manager implements Serializable {

	private static final long serialVersionUID =  6441770236478630564L;

	/**
	 * 管理员ID
	 */
	@Id
	@GeneratedValue
   	@Column(name = "manager_id" )
	private Long managerId;

	/**
	 * 用户ID
	 */
   	@Column(name = "user_id" )
	private Long userId;

	public Long getManagerId() {
		return this.managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TpApiConfig{" +
				"managerId='" + managerId + '\'' +
				"userId='" + userId + '\'' +
				'}';
	}

}
