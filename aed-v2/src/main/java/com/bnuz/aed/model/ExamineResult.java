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
@Table ( name = "ExamineResult" )
public class ExamineResult implements Serializable {

	private static final long serialVersionUID =  2917547055262683005L;

	/**
	 * 审核ID
	 */
	@Id
   	@Column(name = "examine_id" )
	private Long examineId;

	/**
	 * 审核结果
	 */
   	@Column(name = "examine_result" )
	private String examineResult;

	/**
	 * 审核人(管理员)
	 */
   	@Column(name = "manager_id" )
	private Long managerId;

	public Long getExamineId() {
		return this.examineId;
	}

	public void setExamineId(Long examineId) {
		this.examineId = examineId;
	}

	public String getExamineResult() {
		return this.examineResult;
	}

	public void setExamineResult(String examineResult) {
		this.examineResult = examineResult;
	}

	public Long getManagerId() {
		return this.managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	@Override
	public String toString() {
		return "TpApiConfig{" +
				"examineId='" + examineId + '\'' +
				"examineResult='" + examineResult + '\'' +
				"managerId='" + managerId + '\'' +
				'}';
	}

}
