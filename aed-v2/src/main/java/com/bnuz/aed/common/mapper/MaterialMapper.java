package com.bnuz.aed.common.mapper;

import com.bnuz.aed.entity.base.Material;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Leia Liang
 */
@Mapper
public interface MaterialMapper {

    List<Material> findMaterialsByAid(@Param("auditId") Long auditId);

}
