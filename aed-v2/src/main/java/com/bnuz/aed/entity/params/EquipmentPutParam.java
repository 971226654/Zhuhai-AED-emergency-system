package com.bnuz.aed.entity.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * @author Leia Liang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "PUT: 修改AED设备使用的Param类")
public class EquipmentPutParam {

    @ApiModelProperty(value = "AED设备ID", required = true)
    private String equipmentId;

    @ApiModelProperty(value = "所负责该AED设备的检查员ID")
    private String inspectorId;

    @ApiModelProperty(value = "AED设备的摆放时间")
    private String displayTime;

    @ApiModelProperty(value = "AED设备的生产时间")
    private String productionTime;

    @ApiModelProperty(value = "AED设备的购买时间")
    private String purchaseTime;

    @ApiModelProperty(value = "AED设备的厂家名称")
    private String factoryName;

    @ApiModelProperty(value = "AED设备的型号")
    private String model;

    @ApiModelProperty(value = "AED设备是否可用")
    private String status;

}
