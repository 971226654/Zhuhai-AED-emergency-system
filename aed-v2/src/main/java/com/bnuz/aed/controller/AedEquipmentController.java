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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(AedEquipmentController.class);

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
            logger.info("获取成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess(outputs);
        } else {
            logger.error("获取失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/equipments/get/{equipmentId}")
    @ApiOperation("通过equipmentId获得某一个AED设备")
    public ServerResponse getEquipmentById(@PathVariable @ApiParam(value = "设备ID") String equipmentId) {
        Long id = Long.parseLong(equipmentId);
        AedOutput output = aedEquipmentMapper.findEquipmentByIdExpand(id);
        if (output != null) {
            logger.info("获取成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess(output);
        } else {
            logger.error("获取失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/equipments/post")
    @ApiOperation("新增一个AED设备")
    public ServerResponse addEquipment(@Validated EquipmentPostParam params,
                                       @RequestPart(required = false) @ApiParam(value = "设备图片") MultipartFile file) {
        logger.info("params: " + params.toString());
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
            logger.info("fileName: " + file.getOriginalFilename());
            try {
                String url = qiniuCloudUtils.uploadToQiniu(file);
                logger.info("新图片地址：" + url);
                equipment.setAppearance(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            equipment.setAppearance(null);
        }
        logger.info("new equipment: " + equipment.toString());
        int count = aedEquipmentMapper.insertEquipment(equipment);
        if (count > 0) {
            logger.info("新增AED成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            logger.error("新增AED失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/equipments/update")
    @ApiOperation("修改某一个AED设备的基本信息")
    public ServerResponse updateEquipment(@Validated EquipmentPutParam params,
                                          @RequestPart(required = false) @ApiParam(value = "设备图片") MultipartFile file) {
        logger.info("params: " + params.toString());
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
            logger.info("fileName: " + file.getOriginalFilename());
            try {
                String url = qiniuCloudUtils.uploadToQiniu(file);
                logger.info("新图片地址：" + url);
                String oldUrl = aedEquipmentMapper.findImageById(id);
                logger.info("旧图片地址：" + oldUrl);
                if (oldUrl != null) {
                    int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
                    if (statusCode == -1) {
                        logger.error("旧图片删除失败！");
                    } else {
                        logger.info("旧图片删除成功！");
                    }
                }
                equipment.setAppearance(url);
                logger.info("新图片地址：" + url);
                logger.info("图片替换成功！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("update equipment: " + equipment.toString());
        int count = aedEquipmentMapper.updateEquipment(equipment);
        if (count > 0) {
            logger.info("修改AED设备成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("UPDATE SUCCESS!");
        } else {
            logger.error("修改AED设备失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail("NOT UPDATE");
        }

    }

    @DeleteMapping("/equipments/delete/{equipmentId}")
    @ApiOperation("删除一个AED设备的基本信息和地理信息，by 设备ID")
    public ServerResponse deleteEquipment(@PathVariable @ApiParam(value = "设备ID") String equipmentId){
        Long id = Long.parseLong(equipmentId);
        String oldUrl = aedEquipmentMapper.findImageById(id);
        logger.info("旧图片地址：" + oldUrl);
        if (oldUrl != null) {
            int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
            if (statusCode == -1) {
                logger.error("图片删除失败！");
            } else {
                logger.info("图片删除成功！");
            }
        }
        int count3 = aedSituationMapper.deleteRecordByEquipmentId(id);
        int count2 = aedPositionMapper.deletePositionById(id);
        int count1 = aedEquipmentMapper.deleteEquipmentById(id);
        logger.info("删除AED设备（设备信息、位置信息、检查记录） id: " + equipmentId);
        if (count1 > 0) {
            logger.info("删除AED设备成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            logger.error("删除AED设备失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

}
