package com.bnuz.aed.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "反馈的实体类")
public class Feedback implements Serializable {

	private static final long serialVersionUID = -946891381706748100L;

	@ApiModelProperty(value = "反馈ID")
	private Long feedbackId;

	@ApiModelProperty(value = "反馈内容")
	private String feedbackContent;

	@ApiModelProperty(value = "反馈人(用户)")
	private Long userId;

	@ApiModelProperty(value = "反馈星星")
	private int feedbackStars;

	@ApiModelProperty(value = "七牛云图片地址")
	private String picture;

	@ApiModelProperty(value = "反馈时间")
	private String feedbackTime;

}
