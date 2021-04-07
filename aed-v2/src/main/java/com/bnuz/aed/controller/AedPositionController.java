package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AedPositionMapper;
import com.bnuz.aed.common.tools.ResponseCode;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.AedPosition;
import com.bnuz.aed.entity.params.PositionPutParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leia Liang
 */
@RestController
@ResponseBody
@Api(tags = "AedPositionController", description = "AED设备的位置接口")
public class AedPositionController {

    Logger logger = LoggerFactory.getLogger(AedPositionController.class);

    @Autowired
    private AedPositionMapper aedPositionMapper;

    @PostMapping("/positions")
    @ApiOperation("新增设备的地理信息，by 设备的position")
    public ServerResponse addPosition(@Validated AedPosition position) {
        int count = aedPositionMapper.insertPositionByObject(position);
        logger.info("new position: " + position.toString());
        if (count > 0) {
            logger.info("新增Position成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            logger.error("新增Position失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @PutMapping("/positions")
    @ApiOperation("更新一个AED设备的地理信息，by 设备的position")
    public ServerResponse updatePosition(@Validated PositionPutParam params) {
        logger.info("params: " + params.toString());
        AedPosition position = aedPositionMapper.findPositionById(params.getEquipmentId());
        if (!params.getLongitude().equals(position.getLongitude())) {
            position.setLongitude(params.getLongitude());
        }
        if (!params.getLatitude().equals(position.getLatitude())) {
            position.setAddress(params.getLatitude());
        }
        if (!params.getCountry().equals(position.getCountry())) {
            position.setCountry(params.getCountry());
        }
        if (!params.getCity().equals(position.getCity())) {
            position.setCity(params.getCity());
        }
        if (!params.getArea().equals(position.getArea())) {
            position.setArea(params.getArea());
        }
        if (!params.getAddress().equals(position.getAddress())) {
            position.setAddress(params.getAddress());
        }
        int count = aedPositionMapper.updatePositionByObject(position);
        logger.info("update position: " + position.toString());
        if (count > 0) {
            logger.info("更新Position成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess(ResponseCode.SUCCESS.getDesc());
        } else {
            logger.error("更新Position失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail("NOT UPDATE");
        }
    }

    @DeleteMapping("/positions/{equipmentId}")
    @ApiOperation("删除一个AED设备的地理信息，by 设备ID")
    public ServerResponse deletePosition(@PathVariable @ApiParam(value = "设备ID") String equipmentId) {
        Long id = Long.parseLong(equipmentId);
        int count = aedPositionMapper.deletePositionById(id);
        logger.info("删除Position id: " + equipmentId);
        if (count > 0) {
            logger.info("删除Position成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            logger.error("删除Position失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }
}
