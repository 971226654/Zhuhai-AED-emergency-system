package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.CollectListMapper;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.UserAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Leia Liang
 */
@RestController
@ResponseBody
@Api(tags = "CollectListController", description = "收藏模块接口")
public class CollectListController {

    Logger logger = LoggerFactory.getLogger(CollectListController.class);

    @Autowired
    private CollectListMapper collectListMapper;

    @GetMapping("/collections")
    @ApiOperation("通过userId查找他的所有收藏列表")
    public ServerResponse getCollectionsById(HttpServletRequest request) {
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        List<Map<String, Object>> outputs = collectListMapper.findAllCollectById(userId);
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

    @PostMapping("/collections")
    @ApiOperation("通过userId添加一条收藏")
    public ServerResponse addCollection(HttpServletRequest request,
                                        @RequestParam @ApiParam(value = "资讯知识ID") String info_id) {
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        Long infoId = Long.parseLong(info_id);
        int count = collectListMapper.insertCollection(userId, infoId);
        logger.info("new collected id" + info_id);
        if (count > 0) {
            logger.info("新增收藏成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            logger.error("新增收藏失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/collections/{collectionId}")
    @ApiOperation("通过collectionId删除一条收藏")
    public ServerResponse deleteCollection(@PathVariable @ApiParam(value = "收藏ID") String collectionId) {
        Long id = Long.parseLong(collectionId);
        int count = collectListMapper.deleteCollection(id);
        logger.info("删除collection id: " + collectionId);
        if (count > 0) {
            logger.info("删除收藏成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            logger.error("删除收藏失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

}
