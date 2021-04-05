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
@ApiModel(value = "用户的实体类")
public class User implements Serializable {

	private static final long serialVersionUID = 4263260275493328278L;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "微信openid")
	private String wxOpenid;

	@ApiModelProperty(value = "手机号码")
	private String phoneNumber;

	@ApiModelProperty(value = "电子邮箱")
	private String email;

	@ApiModelProperty(value = "身份证")
	private String idCard;

	@ApiModelProperty(value = "负责区域")
	private String responsibleArea;

	@ApiModelProperty(value = "角色")
	private String role;

	@ApiModelProperty(value = "用户密码")
	private String passwd;

}
