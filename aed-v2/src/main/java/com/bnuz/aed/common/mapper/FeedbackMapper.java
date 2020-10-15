package com.bnuz.aed.common.mapper;

import com.bnuz.aed.entity.base.Feedback;
import com.bnuz.aed.entity.base.FeedbackResult;
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

    Map<String, Object> findFeedbackById(@Param("feedbackId") Long feedbackId);

    int insertFeedback(Feedback feedback);

    int insertFeedbackResult(FeedbackResult feedbackResult);

    int deleteFeedback(@Param("feedbackId") Long feedbackId);

    int deleteFeedbackResult(@Param("feedbackId") Long feedbackId);

}
