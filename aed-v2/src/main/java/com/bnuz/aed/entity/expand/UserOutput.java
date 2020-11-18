package com.bnuz.aed.entity.expand;

import com.bnuz.aed.entity.base.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * @author Leia Liang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户信息(包括token)的输出类")
public class UserOutput {

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

    @ApiModelProperty(value = "token")
    private String token;

    public UserOutput(User user) {
        this.userId = user.getUserId();
        this.userName = (!StringUtils.isEmpty(user.getUserName())) ? user.getUserName() : "";
        this.wxOpenid = user.getWxOpenid();
        this.phoneNumber = (!StringUtils.isEmpty(user.getPhoneNumber())) ? user.getUserName() : "";
        this.email = (!StringUtils.isEmpty(user.getEmail())) ? user.getUserName() : "";
        this.idCard = (!StringUtils.isEmpty(user.getIdCard())) ? user.getUserName() : "";
        this.responsibleArea = (!StringUtils.isEmpty(user.getResponsibleArea())) ? user.getUserName() : "";
        this.role = user.getRole();
    }
}
