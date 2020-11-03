package com.bnuz.aed.common.tools.utils;

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
public class CdxException extends RuntimeException {

    private static final long serialVersionUID = 5773366759227293899L;
    @ApiModelProperty(value = "状态码")
    private Integer code;

    private String msg;

}
