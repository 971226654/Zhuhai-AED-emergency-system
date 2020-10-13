package com.bnuz.aed.common.mapper;

import com.bnuz.aed.entity.base.AedEquipment;
import com.bnuz.aed.entity.expand.AedOutput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Leia Liang
 */
@Mapper
public interface AedEquipmentMapper {

    AedOutput findEquipmentByIdExpand(@Param("equipmentId") Long equipmentId);

    AedEquipment findEquipmentByIdBase(@Param("equipmentId") Long equipmentId);

    List<AedOutput> findAllEquipments();

    int insertEquipment(AedEquipment equipment);

    int deleteEquipmentById(@Param("equipmentId") Long equipmentId);

    int updateEquipment(AedEquipment equipment);

    String findImageById(@Param("equipmentId") Long equipmentId);

}
