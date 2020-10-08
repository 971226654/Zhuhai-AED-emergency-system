package com.bnuz.aed.common.mapper;

import com.bnuz.aed.entity.base.RealtimeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Leia Liang
 */
@Mapper
public interface RealtimeInfoMapper {

    List<RealtimeInfo> findAllInfos();

    List<RealtimeInfo> findAllKnows();

    RealtimeInfo findInfoOrKnow(@Param("infoId") Long infoId);

}
