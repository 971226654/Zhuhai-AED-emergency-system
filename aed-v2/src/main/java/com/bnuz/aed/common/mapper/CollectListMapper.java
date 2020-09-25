package com.bnuz.aed.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Leia Liang
 */
@Mapper
public interface CollectListMapper {

    List<Map<String, Object>> findAllCollectById(@Param("userId") Long userId);

}
