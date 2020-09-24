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
public class User implements Serializable {

	private static final long serialVersionUID = 4263260275493328278L;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 微信openid
	 */
	private String wxOpenid;

	/**
	 * 是否是检查员
	 */
	private int inspector;

	/**
	 * 是否是管理员
	 */
	private int manager;

	/**
	 * 手机号码
	 */
	private String phoneNumber;

	/**
	 * 电子邮箱
	 */
	private String email;

	/**
	 * 身份证
	 */
	private String idCard;

	/**
	 * 负责区域
	 */
	private String responsibleArea;

}
