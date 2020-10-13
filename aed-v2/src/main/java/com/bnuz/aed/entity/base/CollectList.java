package com.bnuz.aed.entity.base;

import lombok.*;

import java.io.Serializable;

/**
 * @Description  
 * @Author  Leia Liang
 * @Date 2020-09-20 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectList implements Serializable {

	private static final long serialVersionUID = -8649986576428315969L;

	/** 收藏ID */
	private Long collectionId;

	/** 收藏者(普通用户) */
	private Long userId;

	/** 资讯ID */
	private Long infoId;

}
