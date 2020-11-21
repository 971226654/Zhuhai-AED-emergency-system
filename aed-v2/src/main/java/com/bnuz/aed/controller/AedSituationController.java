package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AedSituationMapper;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.AedSituation;
import com.bnuz.aed.entity.base.UserAuth;
import com.bnuz.aed.entity.params.SituationPostParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Leia Liang
 */
@RestController
@ResponseBody
@Api(tags = "AedSituationController", description = "Aed设备检查记录模块接口")
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

    @GetMapping("/situations/equipment/{equipmentId}")
    @ApiOperation("通过设备ID查询本设备的检查记录")
    public ServerResponse getRecordsByEquipmentId(@PathVariable @ApiParam(value = "设备ID") String equipmentId) {
        Long id = Long.parseLong(equipmentId);
        List<AedSituation> outputs = aedSituationMapper.findRecordsByEquipmentId(id);
        if (outputs != null) {
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/situations/inspector/{inspectorId}")
    @ApiOperation("通过检查员id查询该检查员所检查的所有设备记录")
    public ServerResponse getRecordsByInspectorId(@PathVariable @ApiParam(value = "检查员ID") String inspectorId) {
        Long id = Long.parseLong(inspectorId);
        List<AedSituation> outputs = aedSituationMapper.findRecordsByInspectorId(id);
        if (outputs != null) {
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/situations/{recordId}")
    @ApiOperation("通过记录id查询该条设备检查记录")
    public ServerResponse getRecordByRecordId(@PathVariable @ApiParam(value = "检查记录ID") String recordId) {
        Long id = Long.parseLong(recordId);
        AedSituation situation = aedSituationMapper.findRecordByRecordId(id);
        if (situation != null) {
            return ServerResponse.createBySuccess(situation);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/situations")
    @ApiOperation("添加一条设备检查记录")
    public ServerResponse addRecord(@Validated SituationPostParam params, HttpServletRequest request) {
        System.out.println(params.toString());
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long inspectorId = Long.parseLong(auth.getUserId());
        AedSituation situation = new AedSituation(params);
        situation.setInspectorId(inspectorId);
        int count = aedSituationMapper.insertRecordByObject(situation);
        if (count > 0) {
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PutMapping("/situations")
    @ApiOperation("修改一条设备检查记录，by 记录ID")
    public ServerResponse updateRecord(@Validated AedSituation situation) {
        System.out.println(situation.toString());
        int count = aedSituationMapper.updateRecordByObject(situation);
        if (count > 0) {
            return ServerResponse.createBySuccess("UPDATE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/situations/{recordId}")
    @ApiOperation("删除一条检查记录，by 记录ID")
    public ServerResponse deleteRecord(@PathVariable @ApiParam(value = "检查记录ID") String recordId) {
        Long id = Long.parseLong(recordId);
        int count = aedSituationMapper.deleteRecordByRecordId(id);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

}
