package com.bnuz.aed.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author Leia Liang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "审核材料的实体类")
public class Material implements Serializable {

    private static final long serialVersionUID = -5884655138207608940L;

    @ApiModelProperty(value = "审核ID")
    private Long auditId;

    @ApiModelProperty(value = "材料内容")
    private String materialContent;

    @ApiModelProperty(value = "七牛云图片")
    private String picture;

}
