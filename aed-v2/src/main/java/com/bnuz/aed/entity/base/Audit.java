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
public class Audit implements Serializable {

	private static final long serialVersionUID = 1752438241560823088L;

	/**
	 * 审核ID
	 */
	private Long examineId;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 手机号码
	 */
	private String phoneNumber;

	/**
	 * 身份证
	 */
	private String idCard;


}
