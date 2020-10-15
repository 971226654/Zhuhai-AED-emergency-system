package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.CollectListMapper;
import com.bnuz.aed.common.tools.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "收藏模块接口")
public class CollectListController {

    @Autowired
    private CollectListMapper collectListMapper;

    @GetMapping("/collections/{id}")
    @ApiOperation("通过userId查找他的所有收藏列表")
    public ServerResponse getCollectionsById(@PathVariable String id) {
        Long userId = Long.parseLong(id);
        List<Map<String, Object>> outputs = collectListMapper.findAllCollectById(userId);
        if (outputs != null) {
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/collections/{id}")
    @ApiOperation("通过userId添加一条收藏")
    public ServerResponse addCollection(@PathVariable String id, String info_id) {
        Long userId = Long.parseLong(id);
        Long infoId = Long.parseLong(info_id);
        int count = collectListMapper.insertCollection(userId, infoId);
        if (count > 0) {
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/collections/{id}")
    @ApiOperation("通过collectionId删除一条收藏")
    public ServerResponse deleteCollection(@PathVariable String id) {
        Long collectionId = Long.parseLong(id);
        int count = collectListMapper.deleteCollection(collectionId);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

}
