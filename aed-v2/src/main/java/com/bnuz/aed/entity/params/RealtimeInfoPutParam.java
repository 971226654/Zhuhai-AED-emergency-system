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
@ApiModel(value = "PUT: 修改info的Param类")
public class RealtimeInfoPutParam {

    @ApiModelProperty(value = "资讯id", required = true)
    private String infoId;

    @ApiModelProperty(value = "发布时间")
    private String releaseTime;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "简介")
    private String intro;

    @ApiModelProperty(value = "作者或转载标识")
    private String author;

    @ApiModelProperty(value = "是否是急救知识")
    private String knowledge;

    @ApiModelProperty(value = "是否是资讯")
    private String info;

    @ApiModelProperty(value = "七牛云图片视频地址")
    private String media;
}
