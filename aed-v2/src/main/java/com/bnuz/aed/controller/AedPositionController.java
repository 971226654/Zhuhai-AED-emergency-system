package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AedPositionMapper;
import com.bnuz.aed.common.tools.ResponseCode;
import com.bnuz.aed.common.tools.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "AED设备的位置接口")
public class AedPositionController {

    @Autowired
    private AedPositionMapper aedPositionMapper;

    @PutMapping("/positions/{id}")
    @ApiOperation("更新一个AED设备的经纬度，by 设备ID")
    public ServerResponse updatePosition(@PathVariable String id,
                                         @RequestParam(value = "longitude") String longitude,
                                         @RequestParam(value = "latitude") String latitude) {
        Long equipmentId = Long.parseLong(id);
        int count = aedPositionMapper.updateGeoPosition(equipmentId, longitude, latitude);
        if (count > 0) {
            return ServerResponse.createBySuccess(ResponseCode.SUCCESS.getDesc() + "! Update 1 row");
        } else {
            return ServerResponse.createByFail();
        }
    }

}
