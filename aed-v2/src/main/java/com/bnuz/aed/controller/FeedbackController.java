package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.FeedbackMapper;
import com.bnuz.aed.common.tools.utils.QiniuCloudUtils;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.Feedback;
import com.bnuz.aed.entity.base.FeedbackResult;
import com.bnuz.aed.entity.base.UserAuth;
import com.bnuz.aed.entity.params.FeedbackParam;
import com.bnuz.aed.entity.params.FeedbackResultParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Leia Liang
 */
@RestController
@ResponseBody
@Api(tags = "FeedbackController", description = "反馈模块接口")
public class FeedbackController {

    Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackMapper feedbackMapper;

    private QiniuCloudUtils qiniuCloudUtils = new QiniuCloudUtils();

    @GetMapping("/feedbacks/user")
    @ApiOperation("通过userId查询该用户的反馈记录（带结果）")
    public ServerResponse getFeedbacksByUserId(HttpServletRequest request) {
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        List<Map<String, Object>> outputs = feedbackMapper.findAllFeedbackByUserId(userId);
        if (outputs != null) {
            logger.info("获取成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess(outputs);
        } else {
            logger.error("获取失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/feedbacks/{feedbackId}")
    @ApiOperation("通过feedbackId查询该用户的反馈记录（带结果）")
    public ServerResponse getFeedbackByFeedbackId(@PathVariable @ApiParam(value = "反馈ID") String feedbackId) {
        Long id = Long.parseLong(feedbackId);
        Map<String, Object> output = feedbackMapper.findFeedbackById(id);
        if (output != null) {
            logger.info("获取成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess(output);
        } else {
            logger.error("获取失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/feedbacks")
    @ApiOperation("查询所有反馈记录（带结果）")
    public ServerResponse getAllFeedbacks() {
        List<Map<String, Object>> outputs = feedbackMapper.findAllFeedbacks();
        if (outputs != null) {
            logger.info("获取成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess(outputs);
        } else {
            logger.error("获取失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/feedbacks/count")
    @ApiOperation("查询有多少条反馈记录")
    public ServerResponse getFeedbacksCount() {
        int count = feedbackMapper.sumFeedbacks();
        if (count > 0) {
            logger.info("获取成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess(count);
        } else {
            logger.error("获取失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail("SQL ERROR");
        }
    }

    @GetMapping("/feedbacks/resultCount")
    @ApiOperation("查询有多少条已处理（有结果）的反馈记录")
    public ServerResponse getFeedbackResultsCount() {
        int count = feedbackMapper.sumFeedbackResults();
        if (count > 0) {
            logger.info("获取成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess(count);
        } else {
            logger.error("获取失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail("SQL ERROR");
        }
    }

    @PostMapping("/feedbacks")
    @ApiOperation("通过userId新增一条反馈")
    public ServerResponse addFeedback(HttpServletRequest request,
                                      @Validated FeedbackParam params,
                                      @RequestPart(required = false) @ApiParam(value = "反馈图片") MultipartFile file) {
        logger.info("params: " + params.toString());
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        Feedback feedback = new Feedback();
        feedback.setUserId(userId);
        feedback.setFeedbackStars(Integer.parseInt(params.getFeedbackStars()));
        feedback.setFeedbackContent(params.getFeedbackContent());
        feedback.setFeedbackTime(params.getFeedbackTime());
        if (file != null) {
            logger.info("fileName: " + file.getOriginalFilename());
            try {
                String url = qiniuCloudUtils.uploadToQiniu(file);
                logger.info("新图片地址：" + url);
                feedback.setPicture(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            feedback.setPicture(null);
        }
        int count = feedbackMapper.insertFeedback(feedback);
        logger.info("new feedback: " + feedback.toString());
        if (count > 0) {
            logger.info("新增Feedback成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            logger.error("新增Feedback失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/feedbacks/result")
    @ApiOperation("通过feedbackId新增一条反馈结果")
    public ServerResponse addFeedbackResult(HttpServletRequest request,
                                            @Validated FeedbackResultParam params) {
        logger.info("params: " + params.toString());
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long managerId = Long.parseLong(auth.getUserId());
        FeedbackResult feedbackResult = new FeedbackResult();
        feedbackResult.setFeedbackId(Long.valueOf(params.getFeedbackId()));
        feedbackResult.setResult(params.getResult());
        feedbackResult.setManagerId(managerId);
        feedbackResult.setResultTime(params.getResultTime());
        int count = feedbackMapper.insertFeedbackResult(feedbackResult);
        logger.info("new feedbackResult: " + feedbackResult.toString());
        if (count > 0) {
            logger.info("新增FeedbackResult成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            logger.error("新增FeedbackResult失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/feedbacks/{feedbackId}")
    @ApiOperation("通过feedbackId删除一条反馈")
    public ServerResponse deleteFeedback(@PathVariable @ApiParam(value = "反馈ID") String feedbackId) {
        Long id = Long.parseLong(feedbackId);
        Map<String, Object> feedback = feedbackMapper.findFeedbackById(id);
        if (feedback.get("picture") != null) {
            String oldUrl = (String)feedback.get("picture");
            logger.info("旧图片地址：" + oldUrl);
            if (oldUrl != null) {
                int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
                if (statusCode == -1) {
                    logger.error("图片删除失败！");
                } else {
                    logger.info("图片删除成功！");
                }
            }
        }
        int count2 = feedbackMapper.deleteFeedbackResult(id);
        int count1 = feedbackMapper.deleteFeedback(id);
        logger.info("删除feedback（反馈、结果）id: " + feedbackId);
        if (count1 > 0) {
            logger.info("删除Feedback成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            logger.error("删除Feedback失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/feedbacks/result/{feedbackId}")
    @ApiOperation("通过feedbackId删除一条反馈结果")
    public ServerResponse deleteFeedbackResult(@PathVariable @ApiParam(value = "反馈ID") String feedbackId) {
        Long id = Long.parseLong(feedbackId);
        int count = feedbackMapper.deleteFeedbackResult(id);
        logger.info("删除feedbackResult id: " + feedbackId);
        if (count > 0) {
            logger.info("删除FeedbackResult成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            logger.error("删除FeedbackResult失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

}
