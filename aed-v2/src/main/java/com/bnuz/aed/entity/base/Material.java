package com.bnuz.aed.entity.base;

import lombok.*;

import java.io.Serializable;

/**
 * @author Leia Liang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Material implements Serializable {

    private static final long serialVersionUID = -5884655138207608940L;

    /** 审核ID */
    private Long auditId;

    /** 材料内容 */
    private String materialContent;

    /** 七牛云图片 */
    private String picture;

}
