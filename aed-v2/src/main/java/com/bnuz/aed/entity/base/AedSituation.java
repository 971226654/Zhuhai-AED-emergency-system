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
@ToString
public class AedSituation implements Serializable {

	private static final long serialVersionUID = 4456634333773776256L;

	/** 检查记录ID */
	private Long recordId;

	/** 设备ID */
	private Long equipmentId;

	/** 检查时间 */
	private String inspectTime;

	/** 检查员ID */
	private Long inspectorId;

	/** 设备具体情况 */
	private String recordContent;

	/** 机身有无损坏 */
	private Integer fuselage;

	/** 电极片是否完好 */
	private Integer electrode;

	/** 是否在有效期内 */
	private Integer validity;

	/** 电池是否损坏 */
	private Integer battery;

	/** 目前是否可用 */
	private Integer available;

}
