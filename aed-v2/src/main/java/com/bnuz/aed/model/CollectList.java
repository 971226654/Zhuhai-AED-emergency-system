package com.bnuz.aed.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;

/**
 * @Description  
 * @Author  Leia Liang
 * @Date 2020-09-20 
 */

@Entity
@Table ( name = "CollectList" )
public class CollectList implements Serializable {

	private static final long serialVersionUID =  8057929149234996273L;

	/**
	 * 收藏ID
	 */
	@Id
	@GeneratedValue
   	@Column(name = "collection_id" )
	private Long collectionId;

	/**
	 * 收藏者(普通用户)
	 */
   	@Column(name = "collector" )
	private Long collector;

	/**
	 * 知识ID
	 */
   	@Column(name = "knowledge_id" )
	private Long knowledgeId;

	/**
	 * 资讯ID
	 */
   	@Column(name = "info_id" )
	private Long infoId;

	public Long getCollectionId() {
		return this.collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	public Long getCollector() {
		return this.collector;
	}

	public void setCollector(Long collector) {
		this.collector = collector;
	}

	public Long getKnowledgeId() {
		return this.knowledgeId;
	}

	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public Long getInfoId() {
		return this.infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	@Override
	public String toString() {
		return "TpApiConfig{" +
				"collectionId='" + collectionId + '\'' +
				"collector='" + collector + '\'' +
				"knowledgeId='" + knowledgeId + '\'' +
				"infoId='" + infoId + '\'' +
				'}';
	}

}
