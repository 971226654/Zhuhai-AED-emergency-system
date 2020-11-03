package com.bnuz.aed.entity.expand;

import com.bnuz.aed.entity.base.AuditResult;
import com.bnuz.aed.entity.base.Material;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Leia Liang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditOutput {

    private Long auditId;

    private Long userId;

    private String phoneNumber;

    private String idCard;

    private String auditTime;

    private AuditResult auditResult;

    private Material material;

}
