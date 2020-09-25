package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.CollectListMapper;
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

}
