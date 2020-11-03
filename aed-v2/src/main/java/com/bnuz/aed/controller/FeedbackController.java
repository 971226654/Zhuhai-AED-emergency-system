package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.FeedbackMapper;
import com.bnuz.aed.common.tools.utils.QiniuCloudUtils;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.Feedback;
import com.bnuz.aed.entity.base.FeedbackResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    private QiniuCloudUtils qiniuCloudUtils = new QiniuCloudUtils();

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

    @PostMapping("/feedbacks/{id}")
    @ApiOperation("通过userId新增一条反馈")
    public ServerResponse addFeedback(@PathVariable String id, String feedbackContent, String stars,
                                      @RequestPart MultipartFile file, String feedbackTime) {
        Long userId = Long.parseLong(id);
        int feedbackStars = Integer.parseInt(stars);
        Feedback feedback = new Feedback();
        feedback.setUserId(userId);
        feedback.setFeedbackStars(feedbackStars);
        feedback.setFeedbackContent(feedbackContent);
        feedback.setFeedbackTime(feedbackTime);
        if (!file.isEmpty()) {
            System.out.println(file.getOriginalFilename());
            try {
                String url = qiniuCloudUtils.uploadToQiniu(file);
                feedback.setPicture(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            feedback.setPicture(null);
        }

        int count = feedbackMapper.insertFeedback(feedback);
        if (count > 0) {
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/feedbacks/result/{id}")
    @ApiOperation("通过feedbackId新增一条反馈结果")
    public ServerResponse addFeedbackResult(@PathVariable String id, String result,
                                            String manager_id, String resultTime) {
        Long feedbackId = Long.parseLong(id);
        Long managerId = Long.parseLong(manager_id);
        FeedbackResult feedbackResult = new FeedbackResult();
        feedbackResult.setFeedbackId(feedbackId);
        feedbackResult.setResult(result);
        feedbackResult.setManagerId(managerId);
        feedbackResult.setResultTime(resultTime);
        int count = feedbackMapper.insertFeedbackResult(feedbackResult);
        if (count > 0) {
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/feedbacks/{id}")
    @ApiOperation("通过feedbackId删除一条反馈")
    public ServerResponse deleteFeedback(@PathVariable String id) {
        Long feedbackId = Long.parseLong(id);
        Map<String, Object> feedback = feedbackMapper.findFeedbackById(feedbackId);
        if (feedback.get("picture") != null) {
            String oldUrl = (String)feedback.get("picture");
            int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
            if (statusCode == -1) {
                System.out.println("图片删除失败！");
            } else {
                System.out.println("图片删除成功！");
            }
        }
        int count2 = feedbackMapper.deleteFeedbackResult(feedbackId);
        int count1 = feedbackMapper.deleteFeedback(feedbackId);
        if (count1 > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/feedbacks/result/{id}")
    @ApiOperation("通过feedbackId删除一条反馈结果")
    public ServerResponse deleteFeedbackResult(@PathVariable String id) {
        Long feedbackId = Long.parseLong(id);
        int count = feedbackMapper.deleteFeedbackResult(feedbackId);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

}
