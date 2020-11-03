package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AedSituationMapper;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.AedSituation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "Aed设备检查记录模块接口")
public class AedSituationController {

    @Autowired
    private AedSituationMapper aedSituationMapper;

    private static final String ONE = "1";

    @GetMapping("/situations")
    @ApiOperation("获取所有设备的所有检查记录")
    public ServerResponse getAllRecords() {
        List<AedSituation> outputs = aedSituationMapper.findAllRecords();
        if (outputs != null) {
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/situations/equipmentId/{id}")
    @ApiOperation("通过设备ID查询本设备的检查记录")
    public ServerResponse getRecordsByEquipmentId(@PathVariable String id) {
        Long equipmentId = Long.parseLong(id);
        List<AedSituation> outputs = aedSituationMapper.findRecordsByEquipmentId(equipmentId);
        if (outputs != null) {
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/situations/inspectorId/{id}")
    @ApiOperation("通过检查员id查询该检查员所检查的所有设备记录")
    public ServerResponse getRecordsByInspectorId(@PathVariable String id) {
        Long inspectorId = Long.parseLong(id);
        List<AedSituation> outputs = aedSituationMapper.findRecordsByInspectorId(inspectorId);
        if (outputs != null) {
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/situations/{id}")
    @ApiOperation("通过记录id查询该条设备检查记录")
    public ServerResponse getRecordByRecordId(@PathVariable String id) {
        Long recordId = Long.parseLong(id);
        AedSituation situation = aedSituationMapper.findRecordByRecordId(recordId);
        if (situation != null) {
            return ServerResponse.createBySuccess(situation);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/situations/inspectorId/{id}")
    @ApiOperation("添加一条设备检查记录，by 检查员ID")
    public ServerResponse addRecord(@PathVariable String id, String equipment_id,
                                    String inspectTime, String recordContent,
                                    String fuselage, String electrode,
                                    String validity, String battery,
                                    String available) {
        System.out.println("id:" + id + "\n"
                + "equipment_id:" + equipment_id + "\n"
                + "inspectTime:" + inspectTime + "\n"
                + "recordContent:" + recordContent + "\n"
                + "fuselage:" + fuselage + "\n"
                + "electrode:" + electrode + "\n"
                + "validity:" + validity + "\n"
                + "available:" + available + "\n");
        Long inspectorId = Long.parseLong(id);
        Long equipmentId = Long.parseLong(equipment_id);
        AedSituation situation = new AedSituation();
        situation.setInspectorId(inspectorId);
        situation.setEquipmentId(equipmentId);
        situation.setInspectTime(inspectTime);
        situation.setRecordContent(recordContent);
        situation.setFuselage(Integer.parseInt(fuselage));
        situation.setElectrode(Integer.parseInt(electrode));
        situation.setValidity(Integer.parseInt(validity));
        situation.setBattery(Integer.parseInt(battery));
        situation.setAvailable(Integer.parseInt(available));
        int count = aedSituationMapper.insertRecordByObject(situation);
        if (count > 0) {
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PutMapping("/situations/{id}")
    @ApiOperation("修改一条设备检查记录，by 记录ID")
    public ServerResponse updateRecord(@PathVariable String id, String equipment_id,
                                       String inspector_id, String inspectTime,
                                       String recordContent, String fuselage,
                                       String electrode, String validity,
                                       String battery, String available) {
        Long recordId = Long.parseLong(id);
        Long inspectorId = Long.parseLong(inspector_id);
        Long equipmentId = Long.parseLong(equipment_id);
        AedSituation situation = aedSituationMapper.findRecordByRecordId(recordId);
        situation.setEquipmentId(equipmentId);
        situation.setInspectorId(inspectorId);
        situation.setInspectTime(inspectTime);
        situation.setRecordContent(recordContent);
        situation.setFuselage(Integer.parseInt(fuselage));
        situation.setElectrode(Integer.parseInt(electrode));
        situation.setValidity(Integer.parseInt(validity));
        situation.setBattery(Integer.parseInt(battery));
        situation.setAvailable(Integer.parseInt(available));
        int count = aedSituationMapper.updateRecordByObject(situation);
        if (count > 0) {
            return ServerResponse.createBySuccess("UPDATE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/situations/{id}")
    @ApiOperation("删除一条检查记录，by 记录ID")
    public ServerResponse deleteRecord(@PathVariable String id) {
        Long recordId = Long.parseLong(id);
        int count = aedSituationMapper.deleteRecordByRecordId(recordId);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

}
