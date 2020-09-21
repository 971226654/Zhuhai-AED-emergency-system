package com.bnuz.aed.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description  
 * @Author  Leia Liang
 * @Date 2020-09-20 
 */

@Entity
@Table ( name = "RealtimeInfo" )
public class RealtimeInfo implements Serializable {

	private static final long serialVersionUID =  5908650775694464866L;

	/**
	 * 资讯id
	 */
	@Id
   	@Column(name = "info_id" )
	private Long infoId;

	/**
	 * 发布时间
	 */
   	@Column(name = "release_time" )
	private Date releaseTime;

	/**
	 * 资讯标题
	 */
   	@Column(name = "info_title" )
	private String infoTitle;

	/**
	 * 资讯内容
	 */
   	@Column(name = "info_content" )
	private String infoContent;

	/**
	 * 资讯简介
	 */
   	@Column(name = "info_intro" )
	private String infoIntro;

	/**
	 * 资讯作者或转载标识
	 */
   	@Column(name = "info_author" )
	private String infoAuthor;

	public Long getInfoId() {
		return this.infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	public Date getReleaseTime() {
		return this.releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getInfoTitle() {
		return this.infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getInfoContent() {
		return this.infoContent;
	}

	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}

	public String getInfoIntro() {
		return this.infoIntro;
	}

	public void setInfoIntro(String infoIntro) {
		this.infoIntro = infoIntro;
	}

	public String getInfoAuthor() {
		return this.infoAuthor;
	}

	public void setInfoAuthor(String infoAuthor) {
		this.infoAuthor = infoAuthor;
	}

	@Override
	public String toString() {
		return "TpApiConfig{" +
				"infoId='" + infoId + '\'' +
				"releaseTime='" + releaseTime + '\'' +
				"infoTitle='" + infoTitle + '\'' +
				"infoContent='" + infoContent + '\'' +
				"infoIntro='" + infoIntro + '\'' +
				"infoAuthor='" + infoAuthor + '\'' +
				'}';
	}

}
