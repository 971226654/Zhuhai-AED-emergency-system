package com.bnuz.aed.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description  
 * @Author  Leia Liang
 * @Date 2020-09-20 
 */

@Entity
@Table ( name = "AED-Situation" )
public class AedSituation implements Serializable {

	private static final long serialVersionUID =  3221682358050273265L;

	/**
	 * 检查记录ID
	 */
	@Id
   	@Column(name = "record_id" )
	private Long recordId;

	/**
	 * 设备ID
	 */
   	@Column(name = "equipment_id" )
	private Long equipmentId;

	/**
	 * 检查时间
	 */
   	@Column(name = "inspector_time" )
	private Date inspectorTime;

	/**
	 * 检查员ID
	 */
   	@Column(name = "inspector_id" )
	private Long inspectorId;

	/**
	 * 设备具体情况
	 */
   	@Column(name = "record_content" )
	private String recordContent;

	/**
	 * 机身有无损坏
	 */
   	@Column(name = "fuselage" )
	private Integer fuselage;

	/**
	 * 电极片是否完好
	 */
   	@Column(name = "electrode" )
	private Integer electrode;

	/**
	 * 是否在有效期内
	 */
   	@Column(name = "validity" )
	private Integer validity;

	/**
	 * 电池是否损坏
	 */
   	@Column(name = "battery" )
	private Integer battery;

	/**
	 * 目前是否可用
	 */
   	@Column(name = "available" )
	private Integer available;

	/**
	 * 使用次数
	 */
   	@Column(name = "usetimes" )
	private Long usetimes;

	public Long getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Long getEquipmentId() {
		return this.equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Date getInspectorTime() {
		return this.inspectorTime;
	}

	public void setInspectorTime(Date inspectorTime) {
		this.inspectorTime = inspectorTime;
	}

	public Long getInspectorId() {
		return this.inspectorId;
	}

	public void setInspectorId(Long inspectorId) {
		this.inspectorId = inspectorId;
	}

	public String getRecordContent() {
		return this.recordContent;
	}

	public void setRecordContent(String recordContent) {
		this.recordContent = recordContent;
	}

	public Integer getFuselage() {
		return this.fuselage;
	}

	public void setFuselage(Integer fuselage) {
		this.fuselage = fuselage;
	}

	public Integer getElectrode() {
		return this.electrode;
	}

	public void setElectrode(Integer electrode) {
		this.electrode = electrode;
	}

	public Integer getValidity() {
		return this.validity;
	}

	public void setValidity(Integer validity) {
		this.validity = validity;
	}

	public Integer getBattery() {
		return this.battery;
	}

	public void setBattery(Integer battery) {
		this.battery = battery;
	}

	public Integer getAvailable() {
		return this.available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public Long getUsetimes() {
		return this.usetimes;
	}

	public void setUsetimes(Long usetimes) {
		this.usetimes = usetimes;
	}

	@Override
	public String toString() {
		return "TpApiConfig{" +
				"recordId='" + recordId + '\'' +
				"equipmentId='" + equipmentId + '\'' +
				"inspectorTime='" + inspectorTime + '\'' +
				"inspectorId='" + inspectorId + '\'' +
				"recordContent='" + recordContent + '\'' +
				"fuselage='" + fuselage + '\'' +
				"electrode='" + electrode + '\'' +
				"validity='" + validity + '\'' +
				"battery='" + battery + '\'' +
				"available='" + available + '\'' +
				"usetimes='" + usetimes + '\'' +
				'}';
	}

}
