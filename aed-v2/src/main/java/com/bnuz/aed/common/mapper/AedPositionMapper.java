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

    int updateGeoPosition(@Param("equipmentId") Long equipmentId, @Param("newLongitude") String newLongitude, @Param("newLatitude") String newLatitude);
}
