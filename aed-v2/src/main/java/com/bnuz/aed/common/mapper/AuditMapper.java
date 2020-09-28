package com.bnuz.aed.common.mapper;

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

    List<Map<String, Object>> finAuditsByUserId(@Param("userId") Long userId);

}
