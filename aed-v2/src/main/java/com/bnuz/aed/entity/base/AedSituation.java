package com.bnuz.aed.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Range;

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
@ApiModel(value = "AED设备检查记录的实体类")
public class AedSituation implements Serializable {

	private static final long serialVersionUID = 4456634333773776256L;

	@ApiModelProperty(value = "检查记录ID", required = true)
	private Long recordId;

	@ApiModelProperty(value = "设备ID", required = true)
	private Long equipmentId;

	@ApiModelProperty(value = "检查时间", required = true)
	private String inspectTime;

	@ApiModelProperty(value = "检查员ID", required = true)
	private Long inspectorId;

	@ApiModelProperty(value = "设备具体情况", required = true)
	private String recordContent;

	@ApiModelProperty(value = "机身有无损坏", required = true)
	@Range(min = 0, max = 1)
	private Integer fuselage;

	@ApiModelProperty(value = "电极片是否完好", required = true)
	@Range(min = 0, max = 1)
	private Integer electrode;

	@ApiModelProperty(value = "是否在有效期内", required = true)
	@Range(min = 0, max = 1)
	private Integer validity;

	@ApiModelProperty(value = "电池是否损坏", required = true)
	@Range(min = 0, max = 1)
	private Integer battery;

	@ApiModelProperty(value = "目前是否可用", required = true)
	@Range(min = 0, max = 1)
	private Integer available;

}
