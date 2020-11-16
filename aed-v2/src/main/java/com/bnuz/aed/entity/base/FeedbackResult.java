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
@ApiModel(value = "反馈结果的实体类")
public class FeedbackResult implements Serializable {

	private static final long serialVersionUID = 3797186095642979705L;

	@ApiModelProperty(value = "反馈ID")
	private Long feedbackId;

	@ApiModelProperty(value = "反馈结果")
	private String result;

	@ApiModelProperty(value = "审核人(管理员ID)")
	private Long managerId;

	@ApiModelProperty(value = "处理时间")
	private String resultTime;

}
