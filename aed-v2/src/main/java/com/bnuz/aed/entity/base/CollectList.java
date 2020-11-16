package com.bnuz.aed.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "收藏列表的实体类")
public class CollectList implements Serializable {

	private static final long serialVersionUID = -8649986576428315969L;

	@ApiModelProperty(value = "收藏ID")
	private Long collectionId;

	@ApiModelProperty(value = "收藏者(普通用户)")
	private Long userId;

	@ApiModelProperty(value = "资讯ID")
	private Long infoId;

}
