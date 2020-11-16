package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AedEquipmentMapper;
import com.bnuz.aed.common.mapper.AedPositionMapper;
import com.bnuz.aed.common.tools.utils.QiniuCloudUtils;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.AedEquipment;
import com.bnuz.aed.entity.expand.AedOutput;
import com.bnuz.aed.entity.params.EquipmentPostParam;
import com.bnuz.aed.entity.params.EquipmentPutParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Leia Liang
 */
@RestController
@ResponseBody
@Api(tags = "AedEquipmentController", description = "AED设备接口")
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

    @GetMapping("/equipments/{equipmentId}")
    @ApiOperation("通过equipmentId获得某一个AED设备")
    public ServerResponse getEquipmentById(@PathVariable @ApiParam(value = "设备ID") String equipmentId) {
        Long id = Long.parseLong(equipmentId);
        AedOutput output = aedEquipmentMapper.findEquipmentByIdExpand(id);
        if (output != null) {
            System.out.println(output);
            return ServerResponse.createBySuccess(output);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/equipments")
    @ApiOperation("新增一个AED设备")
    public ServerResponse addEquipment(@Validated EquipmentPostParam params,
                                       @RequestPart @ApiParam(value = "设备图片") MultipartFile file) {
        System.out.println(params.toString());
        AedEquipment equipment = new AedEquipment();
        equipment.setDisplayTime(params.getDisplayTime());
        equipment.setProductionTime(params.getProductionTime());
        if (params.getPurchaseTime().isEmpty()) {
            equipment.setPurchaseTime("null");
        } else {
            equipment.setPurchaseTime(params.getPurchaseTime());
        }
        if (params.getFactoryName().isEmpty()) {
            equipment.setFactoryName("null");
        } else {
            equipment.setFactoryName(params.getFactoryName());
        }
        equipment.setModel(params.getModel());
        equipment.setStatus(params.getStatus());
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

    @PutMapping("/equipments")
    @ApiOperation("修改某一个AED设备的基本信息")
    public ServerResponse updateEquipment(@Validated EquipmentPutParam params,
                                          @RequestPart @ApiParam(value = "设备图片") MultipartFile file) {
        System.out.println(params.toString());
        Long id = params.getEquipmentId();
        AedEquipment equipment = aedEquipmentMapper.findEquipmentByIdBase(id);
        equipment.setInspectorId(params.getInspectorId());
        equipment.setDisplayTime(params.getDisplayTime());
        equipment.setProductionTime(params.getProductionTime());
        equipment.setPurchaseTime(params.getPurchaseTime());
        equipment.setFactoryName(params.getFactoryName());
        equipment.setModel(params.getModel());
        equipment.setStatus(params.getStatus());

        if (!file.isEmpty()) {
            System.out.println(file.getOriginalFilename());
            try {
                String url = qiniuCloudUtils.uploadToQiniu(file);
                String oldUrl = aedEquipmentMapper.findImageById(id);
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

    @DeleteMapping("/equipments/{equipmentId}")
    @ApiOperation("删除一个AED设备的基本信息和地理信息，by 设备ID")
    public ServerResponse deleteEquipment(@PathVariable @ApiParam(value = "设备ID") String equipmentId){
        Long id = Long.parseLong(equipmentId);
        String oldUrl = aedEquipmentMapper.findImageById(id);
        int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
        if (statusCode == -1) {
            System.out.println("图片删除失败！");
        } else {
            System.out.println("图片删除成功！");
        }
        int count1 = aedEquipmentMapper.deleteEquipmentById(id);
        int count2 = aedPositionMapper.deletePositionById(id);
        if (count1 > 0 && count2 > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

}
