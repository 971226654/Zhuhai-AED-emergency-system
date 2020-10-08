package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AedEquipmentMapper;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.AedEquipment;
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
            System.out.println(output);
            return ServerResponse.createBySuccess(output);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/equipments")
    @ApiOperation("新增一个AED设备,所需字段摆放时间,生产时间,购买时间,厂家名称,设备型号,目前状态（是否可用）")
    public ServerResponse addEquipment(@RequestParam(value = "displayTime") String displayTime,
                                       @RequestParam(value = "productionTime") String productionTime,
                                       @RequestParam(value = "purchaseTime") String purchaseTime,
                                       @RequestParam(value = "factoryName") String factoryName,
                                       @RequestParam(value = "model") String model,
                                       @RequestParam(value = "status") int status) {
        AedEquipment equipment = new AedEquipment();
        equipment.setDisplayTime(displayTime);
        equipment.setProductionTime(productionTime);
        equipment.setPurchaseTime(purchaseTime);
        equipment.setFactoryName(factoryName);
        equipment.setModel(model);
        equipment.setStatus(status);
        int count = aedEquipmentMapper.insertEquipment(equipment);
        System.out.println("insert count: " + count);
        if (count > 0) {
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/equipments/{id}")
    @ApiOperation("删除一个AED设备，by 设备ID")
    public ServerResponse deleteEquipment(@PathVariable String id){
        Long equipmentId = Long.parseLong(id);
        int count = aedEquipmentMapper.deleteEquipment(equipmentId);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

}
