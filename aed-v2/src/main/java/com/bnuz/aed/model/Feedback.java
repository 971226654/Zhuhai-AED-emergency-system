package com.bnuz.aed.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.io.InputStream;

/**
 * @Description  
 * @Author  Leia Liang
 * @Date 2020-09-20 
 */

@Entity
@Table ( name = "Feedback" )
public class Feedback implements Serializable {

	private static final long serialVersionUID =  3013877766755556440L;

	/**
	 * 反馈ID
	 */
	@Id
   	@Column(name = "feedback_id" )
	private Long feedbackId;

	/**
	 * 反馈人(用户)
	 */
   	@Column(name = "user_id" )
	private Long userId;

	/**
	 * 反馈内容
	 */
   	@Column(name = "feedback_content" )
	private String feedbackContent;


	public Long getFeedbackId() {
		return this.feedbackId;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFeedbackContent() {
		return this.feedbackContent;
	}

	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}


	@Override
	public String toString() {
		return "TpApiConfig{" +
				"feedbackId='" + feedbackId + '\'' +
				"userId='" + userId + '\'' +
				"feedbackContent='" + feedbackContent + '\'' +
				//"picture='" + picture + '\'' +
				'}';
	}

}
