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
@Table ( name = "Knowledge" )
public class Knowledge implements Serializable {

	private static final long serialVersionUID =  4344866782857981067L;

	/**
	 * 急救知识ID
	 */
	@Id
	@GeneratedValue
   	@Column(name = "knowledge_id" )
	private Long knowledgeId;

	/**
	 * 发布时间
	 */
   	@Column(name = "release_time" )
	private Date releaseTime;

	/**
	 * 急救知识标题
	 */
   	@Column(name = "knowledge_title" )
	private String knowledgeTitle;

	/**
	 * 急救知识内容
	 */
   	@Column(name = "knowledge_content" )
	private String knowledgeContent;

	/**
	 * 急救知识简介
	 */
   	@Column(name = "knowledge_intro" )
	private String knowledgeIntro;

	/**
	 * 急救知识作者或转载标识
	 */
   	@Column(name = "knowledge_author" )
	private String knowledgeAuthor;

	public Long getKnowledgeId() {
		return this.knowledgeId;
	}

	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public Date getReleaseTime() {
		return this.releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getKnowledgeTitle() {
		return this.knowledgeTitle;
	}

	public void setKnowledgeTitle(String knowledgeTitle) {
		this.knowledgeTitle = knowledgeTitle;
	}

	public String getKnowledgeContent() {
		return this.knowledgeContent;
	}

	public void setKnowledgeContent(String knowledgeContent) {
		this.knowledgeContent = knowledgeContent;
	}

	public String getKnowledgeIntro() {
		return this.knowledgeIntro;
	}

	public void setKnowledgeIntro(String knowledgeIntro) {
		this.knowledgeIntro = knowledgeIntro;
	}

	public String getKnowledgeAuthor() {
		return this.knowledgeAuthor;
	}

	public void setKnowledgeAuthor(String knowledgeAuthor) {
		this.knowledgeAuthor = knowledgeAuthor;
	}

	@Override
	public String toString() {
		return "TpApiConfig{" +
				"knowledgeId='" + knowledgeId + '\'' +
				"releaseTime='" + releaseTime + '\'' +
				"knowledgeTitle='" + knowledgeTitle + '\'' +
				"knowledgeContent='" + knowledgeContent + '\'' +
				"knowledgeIntro='" + knowledgeIntro + '\'' +
				"knowledgeAuthor='" + knowledgeAuthor + '\'' +
				'}';
	}

}
