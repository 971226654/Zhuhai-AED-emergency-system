package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.CollectListMapper;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.expand.UserAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "CollectListController", description = "收藏模块接口")
public class CollectListController {

    @Autowired
    private CollectListMapper collectListMapper;

    @GetMapping("/collections")
    @ApiOperation("通过userId查找他的所有收藏列表")
    public ServerResponse getCollectionsById(HttpServletRequest request) {
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        List<Map<String, Object>> outputs = collectListMapper.findAllCollectById(userId);
        if (outputs != null) {
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/collections")
    @ApiOperation("通过userId添加一条收藏")
    public ServerResponse addCollection(HttpServletRequest request,
                                        @RequestParam String info_id) {
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        Long infoId = Long.parseLong(info_id);
        int count = collectListMapper.insertCollection(userId, infoId);
        if (count > 0) {
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/collections/{collectionId}")
    @ApiOperation("通过collectionId删除一条收藏")
    public ServerResponse deleteCollection(@PathVariable String collectionId) {
        Long id = Long.parseLong(collectionId);
        int count = collectListMapper.deleteCollection(id);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

}
