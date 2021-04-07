package com.bnuz.aed.entity.expand;

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
@ApiModel(value = "检查员的输出类")
public class InspectorOutput {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "微信openid")
    private String wxOpenid;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "负责区域")
    private String responsibleArea;

    @ApiModelProperty(value = "角色")
    private String role;

    @ApiModelProperty(value = "用户密码")
    private String passwd;

    @ApiModelProperty(value = "审核ID")
    private Long auditId;

    @ApiModelProperty(value = "审核时间")
    private String auditTime;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "审核结果")
    private String result;

    @ApiModelProperty(value = "审核人(管理员)")
    private Long managerId;

    @ApiModelProperty(value = "处理时间")
    private String resultTime;


}
