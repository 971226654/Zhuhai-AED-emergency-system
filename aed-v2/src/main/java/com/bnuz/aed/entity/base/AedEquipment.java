package com.bnuz.aed.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Range;

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
@ApiModel(value = "AED设备的实体类")
public class AedEquipment implements Serializable {

	private static final long serialVersionUID = -4309564230972272665L;

	@ApiModelProperty(value = "设备ID", required = true)
	private Long equipmentId;

	@ApiModelProperty(value = "检查员ID", required = true)
	private Long inspectorId;

	@ApiModelProperty(value = "摆放时间", required = true)
	private String displayTime;

	@ApiModelProperty(value = "生产时间", required = true)
	private String productionTime;

	@ApiModelProperty(value = "购买时间", required = true)
	private String purchaseTime;

	@ApiModelProperty(value = "厂家名称", required = true)
	private String factoryName;

	@ApiModelProperty(value = "设备型号", required = true)
	private String model;

	@ApiModelProperty(value = "设备状态", required = true)
	@Range(min = 0, max = 1)
	private int status;

	@ApiModelProperty(value = "外观", required = true)
	private String appearance;

}
