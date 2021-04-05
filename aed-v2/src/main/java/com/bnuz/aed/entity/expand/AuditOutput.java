package com.bnuz.aed.entity.expand;

import com.bnuz.aed.entity.base.AuditResult;
import com.bnuz.aed.entity.base.Material;
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
@ApiModel(value = "审核信息的输出类")
public class AuditOutput {

    @ApiModelProperty(value = "审核ID")
    private Long auditId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "审核时间")
    private String auditTime;

    @ApiModelProperty(value = "审核结果")
    private AuditResult auditResult;

    @ApiModelProperty(value = "材料")
    private Material material;

    @ApiModelProperty(value = "姓名")
    private String name;

}
