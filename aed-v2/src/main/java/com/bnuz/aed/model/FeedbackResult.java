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
@Table ( name = "FeedbackResult" )
public class FeedbackResult implements Serializable {

	private static final long serialVersionUID =  683004843580014634L;

	/**
	 * 反馈ID
	 */
	@Id
   	@Column(name = "feedback_id" )
	private Long feedbackId;

	/**
	 * 反馈结果
	 */
   	@Column(name = "result" )
	private String result;

	/**
	 * 审核人(管理员ID)
	 */
   	@Column(name = "manager_id" )
	private Long managerId;

	public Long getFeedbackId() {
		return this.feedbackId;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
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
				"feedbackId='" + feedbackId + '\'' +
				"result='" + result + '\'' +
				"managerId='" + managerId + '\'' +
				'}';
	}

}
