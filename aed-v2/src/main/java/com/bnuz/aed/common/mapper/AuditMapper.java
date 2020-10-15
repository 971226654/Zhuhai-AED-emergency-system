package com.bnuz.aed.common.mapper;

import com.bnuz.aed.entity.base.Audit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Leia Liang
 */
@Mapper
public interface AuditMapper {

    List<Map<String, Object>> findAllAudits();

    Map<String, Object> findAuditsByUserId(@Param("userId") Long userId);

    int insertAudit(Audit audit);

    Audit findAuditByUserId(@Param("userId") Long userId);

    int updateAudit(Audit audit);

    int deleteAudit(@Param("auditId") Long auditId);

}
