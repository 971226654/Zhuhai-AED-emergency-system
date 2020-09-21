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
@Table ( name = "AED-Equipment" )
public class AedEquipment implements Serializable {

	private static final long serialVersionUID =  1684658591110423194L;

	/**
	 * 设备ID
	 */
	@Id
   	@Column(name = "equipment_id" )
	private Long equipmentId;

	/**
	 * 检查员ID
	 */
   	@Column(name = "inspector_id" )
	private Long inspectorId;

	/**
	 * 摆放时间
	 */
   	@Column(name = "display_time" )
	private Date displayTime;

	/**
	 * 生产时间
	 */
   	@Column(name = "production_time" )
	private Date productionTime;

	/**
	 * 购买时间
	 */
   	@Column(name = "purchase_time" )
	private Date purchaseTime;

	/**
	 * 厂家名称
	 */
   	@Column(name = "factory_name" )
	private String factoryName;

	public Long getEquipmentId() {
		return this.equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Long getInspectorId() {
		return this.inspectorId;
	}

	public void setInspectorId(Long inspectorId) {
		this.inspectorId = inspectorId;
	}

	public Date getDisplayTime() {
		return this.displayTime;
	}

	public void setDisplayTime(Date displayTime) {
		this.displayTime = displayTime;
	}

	public Date getProductionTime() {
		return this.productionTime;
	}

	public void setProductionTime(Date productionTime) {
		this.productionTime = productionTime;
	}

	public Date getPurchaseTime() {
		return this.purchaseTime;
	}

	public void setPurchaseTime(Date purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public String getFactoryName() {
		return this.factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	@Override
	public String toString() {
		return "TpApiConfig{" +
				"equipmentId='" + equipmentId + '\'' +
				"inspectorId='" + inspectorId + '\'' +
				"displayTime='" + displayTime + '\'' +
				"productionTime='" + productionTime + '\'' +
				"purchaseTime='" + purchaseTime + '\'' +
				"factoryName='" + factoryName + '\'' +
				'}';
	}

}
