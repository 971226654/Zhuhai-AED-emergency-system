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
@ApiModel(value = "POST: 新增info的Param类")
public class RealtimeInfoParam {

    @ApiModelProperty(value = "发布时间", required = true)
    private String releaseTime;

    @ApiModelProperty(value = "标题", required = true)
    private String title;

    @ApiModelProperty(value = "内容", required = true)
    private String content;

    @ApiModelProperty(value = "简介", required = true)
    private String intro;

    @ApiModelProperty(value = "作者或转载标识", required = true)
    private String author;

    @ApiModelProperty(value = "是否是急救知识", required = true)
    @Range(min = 0, max = 1)
    private int knowledge;

    @ApiModelProperty(value = "是否是资讯", required = true)
    @Range(min = 0, max = 1)
    private int info;

}
