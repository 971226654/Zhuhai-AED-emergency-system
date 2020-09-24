package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AedEquipmentMapper;
import com.bnuz.aed.common.tools.DateUtils;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.expand.AedOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "AED设备接口")
public class AedEquipmentController {

    @Autowired
    private AedEquipmentMapper aedEquipmentMapper;

    @GetMapping("/equipments")
    @ApiOperation("获取所有AED设备信息")
    public ServerResponse getAllEquipments() throws ParseException{
        List<AedOutput> outputs = aedEquipmentMapper.findAllEquipments();
        if (outputs != null) {
            for (AedOutput output : outputs) {
                output.setDisplayTime(DateUtils.DateToDate(output.getDisplayTime()));
                output.setProductionTime(DateUtils.DateToDate(output.getProductionTime()));
                output.setPurchaseTime(DateUtils.DateToDate(output.getPurchaseTime()));
            }
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/equipments/{id}")
    @ApiOperation("通过id获得某一个AED设备")
    public ServerResponse getEquipmentById(@PathVariable String id) throws ParseException {
        Long equipmentId = Long.parseLong(id);
        AedOutput output = aedEquipmentMapper.findEquipmentById(equipmentId);
        if (output != null) {
            output.setDisplayTime(DateUtils.DateToDate(output.getDisplayTime()));
            output.setProductionTime(DateUtils.DateToDate(output.getProductionTime()));
            output.setPurchaseTime(DateUtils.DateToDate(output.getPurchaseTime()));
            return ServerResponse.createBySuccess(output);
        } else {
            return ServerResponse.createByFail();
        }
    }
//
//    @PostMapping("/equipments")
//    @ApiOperation("新增一个AED设备,所需字段购买时间,厂家名称,生产时间, 返回equipmentId")
//    public Long addEquipment(@RequestParam(value = "purchaseTime") String purchaseTime,
//                               @RequestParam(value = "factoryName") String factoryName,
//                               @RequestParam(value = "productionTime") String productionTime) throws ParseException {
//
//        return null;
//    }
//
//    @DeleteMapping("/equipments/delete/{id}")
//    @ApiOperation("删除一个AED设备，by 设备ID")
//    public String deleteEquipment(@PathVariable String id){
//        return null;
//    }

}
