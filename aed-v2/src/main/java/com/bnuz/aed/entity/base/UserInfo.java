package com.bnuz.aed.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Leia Liang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户基本信息的实体类")
public class UserInfo {

    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @ApiModelProperty(value = "手机号码", required = true)
    private String phoneNumber;

    @ApiModelProperty(value = "身份证", required = true)
    private String idCard;

    @ApiModelProperty(value = "电子邮箱", required = true)
    private String email;

    @ApiModelProperty(value = "负责区域", required = true)
    private String responsibleArea;

    @ApiModelProperty(value = "角色", required = true)
    private String role;

    @ApiModelProperty(value = "用户密码", required = true)
    private String passwd;

}
