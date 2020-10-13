package com.bnuz.aed.entity.base;

import lombok.*;

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
public class RealtimeInfo implements Serializable {

	private static final long serialVersionUID = 1997232631727375869L;

	/**
	 * 资讯id
	 */
	private Long infoId;

	/**
	 * 发布时间
	 */
	private String releaseTime;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 简介
	 */
	private String intro;

	/**
	 * 作者或转载标识
	 */
	private String author;

	/**
	 * 是否是急救知识
	 */
	private int knowledge;

	/**
	 * 是否是资讯
	 */
	private int info;

	/**
	 * 七牛云图片视频地址
	 */
	private String media;
}
