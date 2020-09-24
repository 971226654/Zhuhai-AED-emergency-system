package com.bnuz.aed.common.mapper;

import com.bnuz.aed.entity.base.RealtimeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Leia Liang
 */
@Mapper
public interface RealtimeInfoMapper {

    List<RealtimeInfo> findAllInfos();

    List<RealtimeInfo> findAllKnows();

}
