package com.bnuz.aed.entity.expand;

import com.bnuz.aed.entity.base.AuditResult;
import com.bnuz.aed.entity.base.Material;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private AuditResult auditResult;

    private List<Material> materials;

}
