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
@ApiModel(value = "POST/PUT: 增加/修改审核使用的Param类")
public class AuditParam {

    @ApiModelProperty(value = "手机号码", required = true)
    private String phoneNumber;

    @ApiModelProperty(value = "身份证", required = true)
    private String idCard;

    @ApiModelProperty(value = "审核时间", required = true)
    private String auditTime;

    @ApiModelProperty(value = "材料内容", required = true)
    private String materialContent;

}
