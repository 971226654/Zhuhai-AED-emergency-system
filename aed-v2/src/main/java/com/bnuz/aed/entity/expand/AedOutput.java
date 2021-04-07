package com.bnuz.aed.entity.expand;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * @author Leia Liang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "AED设备信息输出类")
public class AedOutput implements Serializable {

    private static final long serialVersionUID = 1167929581558148785L;

    @ApiModelProperty(value = "设备ID")
    private Long equipmentId;

    @ApiModelProperty(value = "检查员ID")
    private Long inspectorId;

    @ApiModelProperty(value = "摆放时间")
    private String displayTime;

    @ApiModelProperty(value = "生产时间")
    private String productionTime;

    @ApiModelProperty(value = "购买时间")
    private String purchaseTime;

    @ApiModelProperty(value = "厂家名称")
    private String factoryName;

    @ApiModelProperty(value = "设备型号")
    private String model;

    @ApiModelProperty(value = "设备状态")
    @Range(min = 0, max = 1)
    private int status;

    @ApiModelProperty(value = "外观")
    private String appearance;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "详细地址")
    private String address;

}
