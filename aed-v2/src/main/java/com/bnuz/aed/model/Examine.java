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
@Table ( name = "Examine" )
public class Examine implements Serializable {

	private static final long serialVersionUID =  869466103952773697L;

	/**
	 * 审核ID
	 */
	@Id
	@GeneratedValue
   	@Column(name = "examine_id" )
	private Long examineId;

	/**
	 * 用户ID
	 */
   	@Column(name = "user_id" )
	private Long userId;

	/**
	 * 材料ID
	 */
   	@Column(name = "material_id" )
	private Long materialId;

	/**
	 * 手机号码
	 */
   	@Column(name = "phonenumber" )
	private String phonenumber;

	/**
	 * 身份证
	 */
   	@Column(name = "idcard" )
	private String idcard;

	public Long getExamineId() {
		return this.examineId;
	}

	public void setExamineId(Long examineId) {
		this.examineId = examineId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getPhonenumber() {
		return this.phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Override
	public String toString() {
		return "TpApiConfig{" +
				"examineId='" + examineId + '\'' +
				"userId='" + userId + '\'' +
				"materialId='" + materialId + '\'' +
				"phonenumber='" + phonenumber + '\'' +
				"idcard='" + idcard + '\'' +
				'}';
	}

}
