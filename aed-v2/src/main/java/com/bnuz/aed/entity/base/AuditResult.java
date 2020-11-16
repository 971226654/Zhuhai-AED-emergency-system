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
@ApiModel(value = "审核结果的实体类")
public class AuditResult implements Serializable {

	private static final long serialVersionUID = -6993144120178080280L;

	@ApiModelProperty(value = "审核ID")
	private Long auditId;

	@ApiModelProperty(value = "审核结果")
	private String result;

	@ApiModelProperty(value = "审核人(管理员)")
	private Long managerId;

	@ApiModelProperty(value = "处理时间")
	private String resultTime;

}
