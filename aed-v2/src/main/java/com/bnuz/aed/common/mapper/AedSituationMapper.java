package com.bnuz.aed.common.mapper;

import com.bnuz.aed.entity.base.AedSituation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Leia Liang
 */
@Mapper
public interface AedSituationMapper {

    List<AedSituation> findAllRecords();

    List<AedSituation> findRecordsByEquipmentId(@Param("equipmentId") Long equipmentId);

    List<AedSituation> findRecordsByInspectorId(@Param("inspectorId") Long inspectorId);

    List<AedSituation> findRecordsByEquipmentIdAndInspectorId(@Param("equipmentId") Long equipmentId, @Param("inspectorId") Long inspectorId);

}
