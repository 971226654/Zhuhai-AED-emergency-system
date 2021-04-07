package com.bnuz.aed.common.mapper;

import com.bnuz.aed.entity.base.Audit;
import com.bnuz.aed.entity.expand.AuditOutput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Leia Liang
 */
@Mapper
public interface AuditMapper {

    List<AuditOutput> findAllAudits();

    List<AuditOutput> findAuditsAllByUserId(@Param("userId") Long userId);

    AuditOutput findAuditAllByAuditId(@Param("auditId") Long auditId);

    int insertAudit(Audit audit);

    Audit findAuditByUserId(@Param("userId") Long userId);

    Audit findAuditByAuditId(@Param("auditId") Long auditId);

    Long[] findAuditIdsByUserId(@Param("userId") Long userId);

    int updateAudit(Audit audit);

    int deleteAudit(@Param("auditId") Long auditId);

}
