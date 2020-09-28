package com.bnuz.aed.common.mapper;

import com.bnuz.aed.entity.base.AuditResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Leia Liang
 */
@Mapper
public interface AuditResultMapper {

    AuditResult findAuditResultByAid(@Param("auditId") Long auditId);

}
