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
@Table ( name = "Inspector" )
public class Inspector implements Serializable {

	private static final long serialVersionUID =  3397744484310796829L;

	/**
	 * 检查员ID
	 */
	@Id
   	@Column(name = "inspector_id" )
	private Long inspectorId;

	/**
	 * 用户ID
	 */
   	@Column(name = "user_id" )
	private Long userId;

	/**
	 * 常住地址
	 */
   	@Column(name = "address" )
	private String address;

	/**
	 * 负责区域
	 */
   	@Column(name = "responsible_area" )
	private String responsibleArea;

	public Long getInspectorId() {
		return this.inspectorId;
	}

	public void setInspectorId(Long inspectorId) {
		this.inspectorId = inspectorId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getResponsibleArea() {
		return this.responsibleArea;
	}

	public void setResponsibleArea(String responsibleArea) {
		this.responsibleArea = responsibleArea;
	}

	@Override
	public String toString() {
		return "TpApiConfig{" +
				"inspectorId='" + inspectorId + '\'' +
				"userId='" + userId + '\'' +
				"address='" + address + '\'' +
				"responsibleArea='" + responsibleArea + '\'' +
				'}';
	}

}
