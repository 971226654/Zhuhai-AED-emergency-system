package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.FeedbackMapper;
import com.bnuz.aed.common.tools.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "反馈模块接口")
public class FeedbackController {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @GetMapping("feedbacks/{id}")
    @ApiOperation("通过userId查询该用户的反馈记录（带结果）")
    public ServerResponse getFeedbacksByUserId(@PathVariable String id) {
        Long userId = Long.parseLong(id);
        List<Map<String, Object>> outputs = feedbackMapper.findAllFeedbackByUserId(userId);
        if (outputs != null) {
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/feedbacks")
    @ApiOperation("查询所有反馈记录（带结果）")
    public ServerResponse getAllFeedbacks() {
        List<Map<String, Object>> outputs = feedbackMapper.findAllFeedbacks();
        if (outputs != null) {
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("feedbacks/count")
    @ApiOperation("查询有多少条反馈记录")
    public ServerResponse getFeedbacksCount() {
        int count = feedbackMapper.sumFeedbacks();
        if (count > 0) {
            return ServerResponse.createBySuccess(count);
        } else {
            return ServerResponse.createByFail("SQL ERROR");
        }
    }

    @GetMapping("/feedbacks/resultCount")
    @ApiOperation("查询有多少条已处理（有结果）的反馈记录")
    public ServerResponse getFeedbackResultsCount() {
        int count = feedbackMapper.sumFeedbackResults();
        if (count > 0) {
            return ServerResponse.createBySuccess(count);
        } else {
            return ServerResponse.createByFail("SQL ERROR");
        }
    }

}
