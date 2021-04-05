package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AedEquipmentMapper;
import com.bnuz.aed.common.mapper.AedPositionMapper;
import com.bnuz.aed.common.mapper.AedSituationMapper;
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

    @Autowired
    private AedSituationMapper aedSituationMapper;

    private QiniuCloudUtils qiniuCloudUtils = new QiniuCloudUtils();

    @GetMapping("/equipments/get")
    @ApiOperation("获取所有AED设备信息")
    public ServerResponse getAllEquipments() {
        List<AedOutput> outputs = aedEquipmentMapper.findAllEquipments();
        if (outputs != null) {
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/equipments/get/{equipmentId}")
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

    @PostMapping("/equipments/post")
    @ApiOperation("新增一个AED设备")
    public ServerResponse addEquipment(@Validated EquipmentPostParam params,
                                       @RequestPart(required = false) @ApiParam(value = "设备图片") MultipartFile file) {
        System.out.println(params.toString());
        AedEquipment equipment = new AedEquipment();
        equipment.setDisplayTime(params.getDisplayTime());
        equipment.setProductionTime(params.getProductionTime());
        if (params.getPurchaseTime() == null) {
            equipment.setPurchaseTime(null);
        } else {
            equipment.setPurchaseTime(params.getPurchaseTime());
        }
        if (params.getFactoryName() == null) {
            equipment.setFactoryName(null);
        } else {
            equipment.setFactoryName(params.getFactoryName());
        }
        equipment.setModel(params.getModel());
        equipment.setStatus(Integer.parseInt(params.getStatus()));
        if (file != null) {
            System.out.println(file.getOriginalFilename());
            try {
                String url = qiniuCloudUtils.uploadToQiniu(file);
                System.out.println("新图片地址：" + url);
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

    @PostMapping("/equipments/update")
    @ApiOperation("修改某一个AED设备的基本信息")
    public ServerResponse updateEquipment(@Validated EquipmentPutParam params,
                                          @RequestPart(required = false) @ApiParam(value = "设备图片") MultipartFile file) {
        System.out.println(params.toString());
        Long id = Long.valueOf(params.getEquipmentId());
        AedEquipment equipment = aedEquipmentMapper.findEquipmentByIdBase(id);
        if (!params.getInspectorId().equals(String.valueOf(equipment.getInspectorId()))) {
            equipment.setInspectorId(Long.valueOf(params.getInspectorId()));
        }
        if (!params.getDisplayTime().equals(equipment.getDisplayTime())) {
            equipment.setDisplayTime(params.getDisplayTime());
        }
        if (!params.getProductionTime().equals(equipment.getProductionTime())) {
            equipment.setProductionTime(params.getProductionTime());
        }
        if (!params.getPurchaseTime().equals(equipment.getPurchaseTime())) {
            equipment.setPurchaseTime(params.getPurchaseTime());
        }
        if (!params.getFactoryName().equals(equipment.getFactoryName())) {
            equipment.setFactoryName(params.getFactoryName());
        }
        if (!params.getModel().equals(equipment.getModel())) {
            equipment.setModel(params.getModel());
        }
        if (!params.getStatus().equals(String.valueOf(equipment.getStatus()))) {
            equipment.setStatus(Integer.parseInt(params.getStatus()));
        }
        if (file != null) {
            System.out.println(file.getOriginalFilename());
            try {
                String url = qiniuCloudUtils.uploadToQiniu(file);
                System.out.println("新图片地址：" + url);
                String oldUrl = aedEquipmentMapper.findImageById(id);
                System.out.println("旧图片地址：" + oldUrl);
                if (oldUrl != null) {
                    int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
                    if (statusCode == -1) {
                        System.out.println("旧图片删除失败！");
                    } else {
                        System.out.println("旧图片删除成功！");
                    }
                }
                equipment.setAppearance(url);
                System.out.println("图片替换成功！");
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

    @DeleteMapping("/equipments/delete/{equipmentId}")
    @ApiOperation("删除一个AED设备的基本信息和地理信息，by 设备ID")
    public ServerResponse deleteEquipment(@PathVariable @ApiParam(value = "设备ID") String equipmentId){
        Long id = Long.parseLong(equipmentId);
        String oldUrl = aedEquipmentMapper.findImageById(id);
        System.out.println("旧图片地址：" + oldUrl);
        if (oldUrl != null) {
            int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
            if (statusCode == -1) {
                System.out.println("图片删除失败！");
            } else {
                System.out.println("图片删除成功！");
            }
        }
        int count3 = aedSituationMapper.deleteRecordByEquipmentId(id);
        int count2 = aedPositionMapper.deletePositionById(id);
        int count1 = aedEquipmentMapper.deleteEquipmentById(id);
        if (count1 > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

}
