package com.bnuz.aed.entity.base;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  
 * @Author  Leia Liang
 * @Date 2020-09-20 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AedEquipment implements Serializable {

	private static final long serialVersionUID = -4309564230972272665L;

	/**
	 * 设备ID
	 */
	private Long equipmentId;

	/**
	 * 检查员ID
	 */
	private Long inspectorId;

	/**
	 * 摆放时间
	 */
	private Date displayTime;

	/**
	 * 生产时间
	 */
	private Date productionTime;

	/**
	 * 购买时间
	 */
	private Date purchaseTime;

	/**
	 * 厂家名称
	 */
	private String factoryName;

	/**
	 * 设备型号
	 */
	private String model;

	/**
	 * 目前是否可用
	 */
	private int status;

	/**
	 * 外观
	 */
	private byte[] appearance;

}
