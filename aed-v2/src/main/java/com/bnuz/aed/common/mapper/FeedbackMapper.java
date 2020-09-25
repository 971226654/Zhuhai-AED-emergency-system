package com.bnuz.aed.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Leia Liang
 */
@Mapper
public interface FeedbackMapper {

    List<Map<String, Object>> findAllFeedbackByUserId(@Param("userId") Long userId);

    int sumFeedbacks();

    int sumFeedbackResults();

    List<Map<String, Object>> findAllFeedbacks();

}
