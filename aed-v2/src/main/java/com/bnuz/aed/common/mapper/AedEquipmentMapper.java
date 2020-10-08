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

    AedOutput findEquipmentById(@Param("equipmentId") Long equipmentId);

    List<AedOutput> findAllEquipments();

    int insertEquipment(AedEquipment equipment);

    int deleteEquipment(@Param("equipmentId") Long equipmentId);

}
