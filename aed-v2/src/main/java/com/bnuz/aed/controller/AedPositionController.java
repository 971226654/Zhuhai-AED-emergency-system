package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AedPositionMapper;
import com.bnuz.aed.common.tools.ResponseCode;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.AedPosition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "AED设备的位置接口")
public class AedPositionController {

    @Autowired
    private AedPositionMapper aedPositionMapper;

    @PostMapping("/positions/{id}")
    @ApiOperation("新增设备的地理信息，by 设备ID")
    public ServerResponse addPosition(@PathVariable String id, String longitude,
                                      String latitude, String country,
                                      String city, String address) {
        Long equipmentId = Long.parseLong(id);
        AedPosition position = new AedPosition();
        position.setEquipmentId(equipmentId);
        position.setLongitude(longitude);
        position.setLatitude(latitude);
        position.setCountry(country);
        position.setCity(city);
        position.setAddress(address);
        int count = aedPositionMapper.insertPositionByObject(position);
        if (count > 0) {
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PutMapping("/positions/{id}")
    @ApiOperation("更新一个AED设备的地理信息，by 设备ID")
    public ServerResponse updatePosition(@PathVariable String id, String longitude,
                                         String latitude, String country,
                                         String city, String address) {
        Long equipmentId = Long.parseLong(id);
        AedPosition position = aedPositionMapper.findPositionById(equipmentId);
        position.setLongitude(longitude);
        position.setLatitude(latitude);
        position.setCountry(country);
        position.setCity(city);
        position.setAddress(address);
        int count = aedPositionMapper.updatePositionByObject(position);
        if (count > 0) {
            return ServerResponse.createBySuccess(ResponseCode.SUCCESS.getDesc() + "! Update 1 row");
        } else {
            return ServerResponse.createByFail("NOT UPDATE");
        }
    }

    @DeleteMapping("/positions/{id}")
    @ApiOperation("删除一个AED设备的地理信息，by 设备ID")
    public ServerResponse deletePosition(@PathVariable String id) {
        Long equipmentId = Long.parseLong(id);
        int count = aedPositionMapper.deletePositionById(equipmentId);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }
}
