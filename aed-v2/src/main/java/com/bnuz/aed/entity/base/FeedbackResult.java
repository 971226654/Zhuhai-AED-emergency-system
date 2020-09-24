package com.bnuz.aed.entity.base;

import lombok.*;

import java.io.Serializable;

/**
 * @Description  
 * @Author  Leia Liang
 * @Date 2020-09-20 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeedbackResult implements Serializable {

	private static final long serialVersionUID = 3797186095642979705L;

	/**
	 * 反馈ID
	 */
	private Long feedbackId;

	/**
	 * 反馈结果
	 */
	private String result;

	/**
	 * 审核人(管理员ID)
	 */
	private Long managerId;

}
