package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AuditMapper;
import com.bnuz.aed.common.tools.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "审核模块接口")
public class AuditController {

    @Autowired
    private AuditMapper auditMapper;

    @GetMapping("/audits")
    @ApiOperation("获取所有审核信息")
    public ServerResponse getAllAudits() {
        List<Map<String, Object>> outputs = auditMapper.findAllAudits();
        if (outputs != null) {
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/audits/{id}")
    @ApiOperation("通过用户ID获取本用户的审核信息")
    public ServerResponse getAuditsById(@PathVariable String id) {
        Long userId = Long.parseLong(id);
        List<Map<String, Object>> outputs = auditMapper.finAuditsByUserId(userId);
        if (outputs != null) {
            return ServerResponse.createBySuccess(outputs);
        } else {
            return ServerResponse.createByFail();
        }
    }

}
