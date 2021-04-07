package com.bnuz.aed.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "POST&PUT: 增加/修改AED设备地理位置使用的Param类")
public class AedPosition implements Serializable {

	private static final long serialVersionUID = -435338639237062148L;

	@ApiModelProperty(value = "设备ID", required = true)
	private Long equipmentId;

	@ApiModelProperty(value = "经度", required = true)
	private String longitude;

	@ApiModelProperty(value = "纬度", required = true)
	private String latitude;

	@ApiModelProperty(value = "国家", required = true)
	private String country;

	@ApiModelProperty(value = "城市", required = true)
	private String city;

	@ApiModelProperty(value = "区", required = true)
	private String area;

	@ApiModelProperty(value = "详细地址", required = true)
	private String address;

}
