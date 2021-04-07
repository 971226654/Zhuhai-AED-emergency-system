package com.bnuz.aed.entity.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Leia Liang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "PUT: 修改PositionParam类")
public class PositionPutParam {

    @ApiModelProperty(value = "设备ID", required = true)
    private Long equipmentId;

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
