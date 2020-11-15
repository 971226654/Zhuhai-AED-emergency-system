package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AedPositionMapper;
import com.bnuz.aed.common.tools.ResponseCode;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.AedPosition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "AedPositionController", description = "AED设备的位置接口")
public class AedPositionController {

    @Autowired
    private AedPositionMapper aedPositionMapper;

    @PostMapping("/positions")
    @ApiOperation("新增设备的地理信息，by 设备的position")
    public ServerResponse addPosition(@Validated AedPosition position) {
        System.out.println(position.toString());
        int count = aedPositionMapper.insertPositionByObject(position);
        if (count > 0) {
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PutMapping("/positions")
    @ApiOperation("更新一个AED设备的地理信息，by 设备的position")
    public ServerResponse updatePosition(@Validated AedPosition position) {
        System.out.println(position.toString());
        int count = aedPositionMapper.updatePositionByObject(position);
        if (count > 0) {
            return ServerResponse.createBySuccess(ResponseCode.SUCCESS.getDesc() + "! Update 1 row");
        } else {
            return ServerResponse.createByFail("NOT UPDATE");
        }
    }

    @DeleteMapping("/positions/{equipmentId}")
    @ApiOperation("删除一个AED设备的地理信息，by 设备ID")
    public ServerResponse deletePosition(@PathVariable String equipmentId) {
        Long id = Long.parseLong(equipmentId);
        int count = aedPositionMapper.deletePositionById(id);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }
}
