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

    RealtimeInfo findInfoOrKnowById(@Param("infoId") Long infoId);

    int insertInfoOrKnow(RealtimeInfo realtimeInfo);

    int updateInfoOrKnow(RealtimeInfo realtimeInfo);

    int deleteInfoOrKnow(@Param("infoId") Long infoId);

    String findMediaById(@Param("infoId") Long infoId);

}
