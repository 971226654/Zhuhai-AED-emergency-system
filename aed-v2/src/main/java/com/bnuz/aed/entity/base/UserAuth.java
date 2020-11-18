package com.bnuz.aed.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Leia Liang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "在request里带的参数，说明用户的ID、角色、将要访问的方法")
public class UserAuth {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户角色")
    private String role;

    @ApiModelProperty(value = "用户访问的接口")
    private String method;

}