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
@ApiModel(value = "审核的实体类")
public class Audit implements Serializable {

	private static final long serialVersionUID = 1752438241560823088L;

	@ApiModelProperty(value = "审核ID")
	private Long auditId;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "手机号码")
	private String phoneNumber;

	@ApiModelProperty(value = "身份证")
	private String idCard;

	@ApiModelProperty(value = "审核时间")
	private String auditTime;

	@ApiModelProperty(value = "姓名")
	private String name;

}
