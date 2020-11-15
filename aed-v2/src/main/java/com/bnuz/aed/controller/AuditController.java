package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.AuditMapper;
import com.bnuz.aed.common.mapper.AuditResultMapper;
import com.bnuz.aed.common.mapper.MaterialMapper;
import com.bnuz.aed.common.tools.utils.QiniuCloudUtils;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.Audit;
import com.bnuz.aed.entity.base.AuditResult;
import com.bnuz.aed.entity.base.Material;
import com.bnuz.aed.entity.expand.UserAuth;
import com.bnuz.aed.entity.params.AuditParam;
import com.bnuz.aed.entity.params.AuditResultParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "AuditController", description = "审核模块接口")
public class AuditController {

    @Autowired
    private AuditMapper auditMapper;

    @Autowired
    private AuditResultMapper auditResultMapper;

    @Autowired
    private MaterialMapper materialMapper;

    private QiniuCloudUtils qiniuCloudUtils = new QiniuCloudUtils();

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

    @GetMapping("/audits/user")
    @ApiOperation("通过用户ID获取本用户的审核信息")
    public ServerResponse getAuditsByUserId(HttpServletRequest request) {
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        Map<String, Object> output = auditMapper.findAuditsByUserId(userId);
        if (output != null) {
            return ServerResponse.createBySuccess(output);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/audits/{auditId}")
    @ApiOperation("通过审核ID获取本用户的审核信息")
    public ServerResponse getAuditById(@PathVariable String auditId) {
        Long id = Long.parseLong(auditId);
        Map<String, Object> output = auditMapper.findAuditByAuditId(id);
        if (output != null) {
            return ServerResponse.createBySuccess(output);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/audits")
    @ApiOperation("通过用户ID新增一个审核")
    public ServerResponse addAudit(HttpServletRequest request,
                                   @Validated AuditParam params,
                                   @RequestPart MultipartFile file) {
        System.out.println(params.toString());
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        Audit audit = new Audit();
        audit.setUserId(userId);
        audit.setPhoneNumber(params.getPhoneNumber());
        audit.setIdCard(params.getIdCard());
        audit.setAuditTime(params.getAuditTime());
        int count1 = auditMapper.insertAudit(audit);
        Long auditId = auditMapper.findAuditByUserId(userId).getAuditId();
        int count2 = 0;
        if (!params.getMaterialContent().isEmpty()) {
            Material material = new Material();
            material.setAuditId(auditId);
            material.setMaterialContent(params.getMaterialContent());
            if (!file.isEmpty()) {
                System.out.println(file.getOriginalFilename());
                try {
                    String url = qiniuCloudUtils.uploadToQiniu(file);
                    material.setPicture(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                material.setPicture(null);
            }
            count2 = materialMapper.insertMaterial(material);
        }
        System.out.println("count2: " + count2);

        if (count1 > 0) {
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/audits/result")
    @ApiOperation("通过auditId使管理员新增一个审核结果")
    public ServerResponse addAuditResult(HttpServletRequest request,
                                         @Validated AuditResultParam params) {
        System.out.println(params.toString());
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long managerId = Long.parseLong(auth.getUserId());
        AuditResult auditResult = new AuditResult();
        auditResult.setAuditId(params.getAuditId());
        auditResult.setResult(params.getResult());
        auditResult.setManagerId(managerId);
        auditResult.setResultTime(params.getResultTime());

        int count = auditResultMapper.insertAuditResult(auditResult);
        if (count > 0) {
            return ServerResponse.createBySuccess("INSERT SUCCESS");
            //TODO: 修改该用户的权限
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PutMapping("/audits")
    @ApiOperation("通过用户ID修改一个审核")
    public ServerResponse updateAudit(HttpServletRequest request,
                                      @Validated AuditParam params,
                                      @RequestPart MultipartFile file) {
        System.out.println(params.toString());
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        Audit audit = auditMapper.findAuditByUserId(userId);
        Long auditId = audit.getAuditId();
        audit.setPhoneNumber(params.getPhoneNumber());
        audit.setIdCard(params.getIdCard());
        audit.setAuditTime(params.getAuditTime());
        int count1 = auditMapper.updateAudit(audit);
        int count2 = 0;
        Material material = materialMapper.findMaterialsByAid(auditId);
        if (material != null) {
            material.setMaterialContent(params.getMaterialContent());
            if (!file.isEmpty()) {
                System.out.println(file.getOriginalFilename());
                try {
                    String url = qiniuCloudUtils.uploadToQiniu(file);
                    String oldUrl = material.getPicture();
                    int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
                    if (statusCode == -1) {
                        System.out.println("图片删除失败！");
                    } else {
                        System.out.println("图片删除成功！");
                    }
                    material.setPicture(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            count2 = materialMapper.updateMaterial(material);
        } else {
            Material material_new = new Material();
            material_new.setAuditId(auditId);
            material_new.setMaterialContent(params.getMaterialContent());
            if (!file.isEmpty()) {
                System.out.println(file.getOriginalFilename());
                try {
                    String url = qiniuCloudUtils.uploadToQiniu(file);
                    material_new.setPicture(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                material_new.setPicture(null);
            }
            count2 = materialMapper.insertMaterial(material_new);
        }
        System.out.println("count2: " + count2);

        if (count1 > 0) {
            return ServerResponse.createBySuccess("UPDATE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PutMapping("/audits/result")
    @ApiOperation("通过auditId使管理员修改一个审核结果")
    public ServerResponse updateAuditResult(HttpServletRequest request,
                                            @Validated AuditResultParam params) {
        System.out.println(params.toString());
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long managerId = Long.parseLong(auth.getUserId());
        Long auditId = params.getAuditId();
        AuditResult auditResult = auditResultMapper.findAuditResultByAid(auditId);
        auditResult.setResult(params.getResult());
        auditResult.setManagerId(managerId);
        auditResult.setResultTime(params.getResultTime());
        int count = auditResultMapper.updateAuditResult(auditResult);
        if (count > 0) {
            return ServerResponse.createBySuccess("UPDATE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/audits/{auditId}")
    @ApiOperation("通过auditId删除一个审核")
    public ServerResponse deleteAudit(@PathVariable String auditId) {
        String msg = "";
        Long id = Long.parseLong(auditId);
        int count1 = auditResultMapper.deleteAuditResult(id);
        if (count1 == 0) {
            msg += "该审核没有结果，";
        } else {
            msg += "该审核有结果，";
        }
        String oldUrl = materialMapper.findPictureByAid(id);
        int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
        if (statusCode == -1) {
            System.out.println("图片删除失败！");
        } else {
            System.out.println("图片删除成功！");
        }
        int count2 = materialMapper.deleteMaterial(id);
        if (count2 == 0) {
            msg += "该审核没有材料，";
        } else {
            msg += "该审核有材料，";
        }
        int count3 = auditMapper.deleteAudit(id);
        if (count3 > 0) {
            msg += "审核删除成功。";
            return ServerResponse.createBySuccess(msg);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/audits/result/{auditId}")
    @ApiOperation("通过auditId删除一个审核结果")
    public ServerResponse deleteAuditResult(@PathVariable String auditId) {
        Long id = Long.parseLong(auditId);
        int count = auditResultMapper.deleteAuditResult(id);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/materials/{auditId}")
    @ApiOperation("通过auditId删除一个审核材料")
    public ServerResponse deleteMaterial(@PathVariable String auditId) {
        Long id = Long.parseLong(auditId);
        int count = materialMapper.deleteMaterial(id);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

}
