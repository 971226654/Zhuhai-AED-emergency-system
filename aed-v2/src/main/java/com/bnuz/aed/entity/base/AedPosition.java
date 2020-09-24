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
public class AedPosition implements Serializable {

	private static final long serialVersionUID = -435338639237062148L;

	/**
	 * 设备ID
	 */
	private Long equipmentId;

	/**
	 * 经度
	 */
	private String longitude;

	/**
	 * 纬度
	 */
	private String latitude;

	/**
	 * 国家或地区
	 */
	private String country;

	/**
	 * 城市
	 */
	private String city;

	/**
	 * 详细地址
	 */
	private String address;

}
