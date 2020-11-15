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
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "POST/PUT: 增加/修改审核结果的Param类")
public class AuditResultParam {

    @ApiModelProperty(value = "审核ID", required = true)
    private Long auditId;

    @ApiModelProperty(value = "审核结果", required = true)
    private String result;

    @ApiModelProperty(value = "处理时间", required = true)
    private String resultTime;

}
