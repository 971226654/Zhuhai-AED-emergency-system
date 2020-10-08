package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.RealtimeInfoMapper;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.RealtimeInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "资讯模块接口")
public class RealtimeInfoController {

    @Autowired
    private RealtimeInfoMapper realtimeInfoMapper;

    @GetMapping("/infos/info")
    @ApiOperation("获取所有资讯")
    public ServerResponse getAllInfos() {
        List<RealtimeInfo> infos = realtimeInfoMapper.findAllInfos();
        if (infos != null) {
            return ServerResponse.createBySuccess(infos);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/infos/know")
    @ApiOperation("获取所有急救知识")
    public ServerResponse getAllKnows() {
        List<RealtimeInfo> knows = realtimeInfoMapper.findAllKnows();
        if (knows != null) {
            return ServerResponse.createBySuccess(knows);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/infos/{id}")
    @ApiOperation("根据id获取资讯或急救知识")
    public ServerResponse getInfoOrKnow(@PathVariable String id) {
        Long infoId = Long.parseLong(id);
        RealtimeInfo info = realtimeInfoMapper.findInfoOrKnow(infoId);
        if (info != null) {
            return ServerResponse.createBySuccess(info);
        } else {
            return ServerResponse.createByFail();
        }
    }

}
