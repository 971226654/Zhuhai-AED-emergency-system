package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AedSituationMapper;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.AedSituation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "Aed设备检查记录模块接口")
public class AedSituationController {

    @Autowired
    private AedSituationMapper aedSituationMapper;

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

}
