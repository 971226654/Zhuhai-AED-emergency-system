package com.bnuz.aed.controller;

import com.bnuz.aed.common.repository.AedEquipmentRepository;
import com.bnuz.aed.common.repository.AedPositionRepository;
import com.bnuz.aed.common.tools.DateUtils;
import com.bnuz.aed.common.tools.JsonUtils;
import com.bnuz.aed.model.AedEquipment;
import com.bnuz.aed.model.AedPosition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Optional;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "AED设备接口")
public class AedEquipmentController {

    @Autowired
    private AedEquipmentRepository aedEquipmentRepository;
    @Autowired
    private AedPositionRepository aedPositionRepository;

    @GetMapping("/equipments")
    @ApiOperation("获取所有AED设备信息")
    public String getAllEquipments() {
        if (aedEquipmentRepository.findAllEquipments() != null) {
            return JsonUtils.objectToJson(aedEquipmentRepository.findAllEquipments());
        }
        return null;
    }

    @GetMapping("/equipments/{id}")
    @ApiOperation("通过id获得某一个AED设备")
    public String getEquipmentById(@PathVariable String id) {
        Long equipmentId = Long.parseLong("id");
        if (aedEquipmentRepository.findEquipmentById(equipmentId) != null) {
            return JsonUtils.objectToJson(aedEquipmentRepository.findEquipmentById(equipmentId));
        }
        return null;
    }

    @PostMapping("/equipments")
    @ApiOperation("新增一个AED设备")
    public String addEquipment(@RequestParam(value = "purchaseTime") String purchaseTime,
                               @RequestParam(value = "factoryName") String factoryName,
                               @RequestParam(value = "productionTime") String productionTime,
                               @RequestParam(value = "longitude") String longitude,
                               @RequestParam(value = "latitude") String latitude) throws ParseException {
        AedEquipment equipment = new AedEquipment();
        equipment.setPurchaseTime(DateUtils.stringToDate(purchaseTime));
        equipment.setFactoryName(factoryName);
        equipment.setProductionTime(DateUtils.stringToDate(productionTime));
        aedEquipmentRepository.save(equipment);
        long id = aedEquipmentRepository.findIdByTimes(purchaseTime, factoryName, productionTime);
        AedPosition position = new AedPosition();
        position.setEquipmentId(id);
        position.setLongitude(longitude);
        position.setLatitude(latitude);
        aedPositionRepository.save(position);
        return "OK";
    }

    @DeleteMapping("/equipments/delete/{id}")
    @ApiOperation("删除一个AED设备")
    public String deleteEquipment(@PathVariable String id){
        Long equipmentId = Long.parseLong(id);
        Optional<AedEquipment> optional = aedEquipmentRepository.findById(equipmentId);
        if (optional.isPresent()) {
            aedEquipmentRepository.delete(optional.get());
            return "OK";
        } else {
            return "Fail";
        }
    }

    @PutMapping("/equipments/position/{id}")
    @ApiOperation("更新一个AED设备的经纬度")
    public String updatePosition(@PathVariable String id,
                                 @RequestParam(value = "longitude") String longitude,
                                 @RequestParam(value = "latitude") String latitude) {
        Long equipmentId = Long.parseLong("id");
        Optional<AedPosition> optional = aedPositionRepository.findById(equipmentId);
        if (optional.isPresent()) {
            AedPosition position = optional.get();
            position.setLongitude(longitude);
            position.setLatitude(latitude);
            aedPositionRepository.save(position);
            return "OK";
        } else {
            return "Fail";
        }
    }

}
