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

    /**
     * 材料ID
     */
    private Long materialId;

    /**
     * 材料内容
     */
    private String materialContent;

    /**
     * 审核ID
     */
    private Long auditId;

}
