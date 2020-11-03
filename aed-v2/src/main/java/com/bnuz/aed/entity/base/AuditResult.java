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
public class AuditResult implements Serializable {

	private static final long serialVersionUID = -6993144120178080280L;

	/** 审核ID */
	private Long auditId;

	/** 审核结果 */
	private String result;

	/** 审核人(管理员) */
	private Long managerId;

	/** 处理时间 */
	private String resultTime;

}
