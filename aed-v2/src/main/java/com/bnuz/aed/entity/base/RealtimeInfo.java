package com.bnuz.aed.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  
 * @Author  Leia Liang
 * @Date 2020-09-20 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "资讯实体类")
public class RealtimeInfo implements Serializable {

	private static final long serialVersionUID = 1997232631727375869L;

	@ApiModelProperty(value = "资讯id")
	private Long infoId;

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
	@Range(min = 0, max = 1)
	private int knowledge;

	@ApiModelProperty(value = "是否是资讯")
	@Range(min = 0, max = 1)
	private int info;

	@ApiModelProperty(value = "七牛云图片视频地址")
	private String media;
}
