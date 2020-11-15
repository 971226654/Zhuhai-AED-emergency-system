package com.bnuz.aed.common.mapper;

import com.bnuz.aed.entity.base.AedSituation;
import com.bnuz.aed.entity.params.SituationPostParam;
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

    int insertRecordByObject(SituationPostParam param);

    int updateRecordByObject(AedSituation situation);

    int deleteRecordByRecordId(@Param("recordId") Long recordId);

    AedSituation findRecordByRecordId(@Param("recordId") Long recordId);

}
