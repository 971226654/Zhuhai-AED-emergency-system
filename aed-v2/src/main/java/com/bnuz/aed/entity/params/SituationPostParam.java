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
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "POST: 增加AED检查记录的Param类")
public class SituationPostParam {

    @ApiModelProperty(value = "AED设备ID", required = true)
    private Long equipmentId;

    @ApiModelProperty(value = "AED设备检查时间", required = true)
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
