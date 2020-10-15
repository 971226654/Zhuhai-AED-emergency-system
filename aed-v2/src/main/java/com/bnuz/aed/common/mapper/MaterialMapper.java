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

    Material findMaterialsByAid(@Param("auditId") Long auditId);

    String findPictureByAid(@Param("auditId") Long auditId);

    int insertMaterial(Material material);

    int updateMaterial(Material material);

    int deleteMaterial(@Param("auditId") Long auditId);

}
