package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AedEquipmentMapper;
import com.bnuz.aed.common.mapper.AedPositionMapper;
import com.bnuz.aed.common.tools.QiniuCloudUtils;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.AedEquipment;
import com.bnuz.aed.entity.expand.AedOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "AED设备接口")
public class AedEquipmentController {

    @Autowired
    private AedEquipmentMapper aedEquipmentMapper;

    @Autowired
    private AedPositionMapper aedPositionMapper;

    private QiniuCloudUtils qiniuCloudUtils = new QiniuCloudUtils();

    @GetMapping("/equipments")
    @ApiOperation("获取所有AED设备信息")
    public ServerResponse getAllEquipments() {
        List<AedOutput> outputs = aedEquipmentMapper.findAllEquipments();
        if (outputs != null) {
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/equipments/{id}")
    @ApiOperation("通过id获得某一个AED设备")
    public ServerResponse getEquipmentById(@PathVariable String id) {
        Long equipmentId = Long.parseLong(id);
        AedOutput output = aedEquipmentMapper.findEquipmentByIdExpand(equipmentId);
        if (output != null) {
            System.out.println(output);
            return ServerResponse.createBySuccess(output);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/equipments")
    @ApiOperation("新增一个AED设备,所需字段摆放时间,生产时间,购买时间,厂家名称,设备型号,目前状态（是否可用）,外观照片")
    public ServerResponse addEquipment(String displayTime, String productionTime,
                                       String purchaseTime, String factoryName,
                                       String model, int status,
                                       @RequestPart MultipartFile file) {
        AedEquipment equipment = new AedEquipment();
        equipment.setDisplayTime(displayTime);
        equipment.setProductionTime(productionTime);
        equipment.setPurchaseTime(purchaseTime);
        equipment.setFactoryName(factoryName);
        equipment.setModel(model);
        equipment.setStatus(status);
        if (!file.isEmpty()) {
            System.out.println(file.getOriginalFilename());
            try {
                String url = qiniuCloudUtils.uploadToQiniu(file);
                equipment.setAppearance(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            equipment.setAppearance(null);
        }
        int count = aedEquipmentMapper.insertEquipment(equipment);
        System.out.println("insert count: " + count);
        if (count > 0) {
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PutMapping("/equipments/{id}")
    @ApiOperation("修改某一个AED设备的基本信息，by 设备ID，所需字段同post")
    public ServerResponse updateEquipment(@PathVariable String id, String inspector_id,
                                          String displayTime, String productionTime,
                                          String purchaseTime, String factoryName,
                                          String model, int status,
                                          @RequestPart MultipartFile file) {
        Long equipmentId = Long.parseLong(id);
        AedEquipment equipment = aedEquipmentMapper.findEquipmentByIdBase(equipmentId);

        Long inspectorId = Long.parseLong(inspector_id);
        equipment.setInspectorId(inspectorId);
        equipment.setDisplayTime(displayTime);
        equipment.setProductionTime(productionTime);
        equipment.setPurchaseTime(purchaseTime);
        equipment.setFactoryName(factoryName);
        equipment.setModel(model);
        equipment.setStatus(status);

        if (!file.isEmpty()) {
            System.out.println(file.getOriginalFilename());
            try {
                String url = qiniuCloudUtils.uploadToQiniu(file);
                String oldUrl = aedEquipmentMapper.findImageById(equipmentId);
                int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
                if (statusCode == -1) {
                    System.out.println("图片删除失败！");
                } else {
                    System.out.println("图片删除成功！");
                }
                equipment.setAppearance(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int count = aedEquipmentMapper.updateEquipment(equipment);
        if (count > 0) {
            return ServerResponse.createBySuccess("UPDATE SUCCESS!");
        } else {
            return ServerResponse.createByFail("NOT UPDATE");
        }

    }

    @DeleteMapping("/equipments/{id}")
    @ApiOperation("删除一个AED设备的基本信息和地理信息，by 设备ID")
    public ServerResponse deleteEquipment(@PathVariable String id){
        Long equipmentId = Long.parseLong(id);
        String oldUrl = aedEquipmentMapper.findImageById(equipmentId);
        int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
        if (statusCode == -1) {
            System.out.println("图片删除失败！");
        } else {
            System.out.println("图片删除成功！");
        }
        int count1 = aedEquipmentMapper.deleteEquipmentById(equipmentId);
        int count2 = aedPositionMapper.deletePositionById(equipmentId);
        if (count1 > 0 && count2 > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

}
