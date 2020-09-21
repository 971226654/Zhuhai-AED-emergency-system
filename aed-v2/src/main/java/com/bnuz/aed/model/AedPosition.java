package com.bnuz.aed.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Description  
 * @Author  Leia Liang
 * @Date 2020-09-20 
 */

@Entity
@Table ( name = "aed_position" )
public class AedPosition implements Serializable {

	private static final long serialVersionUID =  4049388573756599450L;

	/**
	 * 设备ID
	 */
	@Id
   	@Column(name = "equipment_id" )
	private Long equipmentId;

	/**
	 * 经度
	 */
   	@Column(name = "longitude" )
	private String longitude;

	/**
	 * 纬度
	 */
   	@Column(name = "latitude" )
	private String latitude;

	/**
	 * 国家或地区
	 */
   	@Column(name = "country" )
	private String country;

	/**
	 * 城市
	 */
   	@Column(name = "city" )
	private String city;

	/**
	 * 详细地址
	 */
   	@Column(name = "address" )
	private String address;

	public Long getEquipmentId() {
		return this.equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "TpApiConfig{" +
				"equipmentId='" + equipmentId + '\'' +
				"longitude='" + longitude + '\'' +
				"latitude='" + latitude + '\'' +
				"country='" + country + '\'' +
				"city='" + city + '\'' +
				"address='" + address + '\'' +
				'}';
	}

}
