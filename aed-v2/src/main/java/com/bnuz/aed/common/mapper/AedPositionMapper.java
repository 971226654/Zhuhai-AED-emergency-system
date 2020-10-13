package com.bnuz.aed.common.mapper;

import com.bnuz.aed.entity.base.AedPosition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Leia Liang
 */
@Mapper
public interface AedPositionMapper {

    AedPosition findPositionById(@Param("equipmentId") Long equipmentId);

    int updatePositionByObject(AedPosition position);

    int insertPositionByObject(AedPosition position);

    int deletePositionById(@Param("equipmentId") Long equipmentId);

}
