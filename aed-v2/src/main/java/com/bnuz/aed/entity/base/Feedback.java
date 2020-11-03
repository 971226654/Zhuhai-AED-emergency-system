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
public class Feedback implements Serializable {

	private static final long serialVersionUID = -946891381706748100L;

	/** 反馈ID */
	private Long feedbackId;

	/** 反馈内容 */
	private String feedbackContent;

	/** 反馈人(用户) */
	private Long userId;

	/** 反馈星星 */
	private int feedbackStars;

	/** 七牛云图片地址 */
	private String picture;

	/** 反馈时间 */
	private String feedbackTime;

}
