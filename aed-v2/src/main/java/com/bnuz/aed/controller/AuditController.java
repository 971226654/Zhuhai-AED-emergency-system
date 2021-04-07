package com.bnuz.aed.controller;

import cn.hutool.core.util.ArrayUtil;
import com.bnuz.aed.common.mapper.AuditMapper;
import com.bnuz.aed.common.mapper.AuditResultMapper;
import com.bnuz.aed.common.mapper.MaterialMapper;
import com.bnuz.aed.common.mapper.UserMapper;
import com.bnuz.aed.common.tools.utils.QiniuCloudUtils;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.Audit;
import com.bnuz.aed.entity.base.AuditResult;
import com.bnuz.aed.entity.base.Material;
import com.bnuz.aed.entity.base.UserAuth;
import com.bnuz.aed.entity.expand.AuditOutput;
import com.bnuz.aed.entity.params.AuditParam;
import com.bnuz.aed.entity.params.AuditResultParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@ResponseBody
@Api(tags = "AuditController", description = "审核模块接口")
public class AuditController {

    Logger logger = LoggerFactory.getLogger(AuditController.class);

    @Autowired
    private AuditMapper auditMapper;

    @Autowired
    private AuditResultMapper auditResultMapper;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private UserMapper userMapper;

    private QiniuCloudUtils qiniuCloudUtils = new QiniuCloudUtils();

    @GetMapping("/audits")
    @ApiOperation("获取所有审核信息")
    public ServerResponse getAllAudits() {
        List<AuditOutput> outputs = auditMapper.findAllAudits();
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

    @GetMapping("/audits/user")
    @ApiOperation("通过用户ID获取本用户的审核信息")
    public ServerResponse getAuditsByUserId(HttpServletRequest request) {
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        List<AuditOutput> output = auditMapper.findAuditsAllByUserId(userId);
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

    @GetMapping("/audits/{auditId}")
    @ApiOperation("通过审核ID获取本用户的审核信息")
    public ServerResponse getAuditById(@PathVariable @ApiParam(value = "审核ID") String auditId) {
        Long id = Long.parseLong(auditId);
        AuditOutput output = auditMapper.findAuditAllByAuditId(id);
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

    @PostMapping("/audits")
    @ApiOperation("通过用户ID新增一个审核")
    public ServerResponse addAudit(HttpServletRequest request,
                                   @Validated AuditParam params,
                                   @RequestPart(required = false) @ApiParam(value = "材料图片") MultipartFile file) {
        logger.info("params: " + params.toString());
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        Audit audit = new Audit();
        audit.setUserId(userId);
        audit.setPhoneNumber(params.getPhoneNumber());
        audit.setIdCard(params.getIdCard());
        audit.setAuditTime(params.getAuditTime());
        audit.setRealName(params.getRealName());
        int count1 = auditMapper.insertAudit(audit);
        logger.info("new audit: " + audit.toString());

        Long auditId = ArrayUtil.max(auditMapper.findAuditIdsByUserId(userId));
        int count2 = 0;
        if (!params.getMaterialContent().isEmpty()) {
            Material material = new Material();
            material.setAuditId(auditId);
            material.setMaterialContent(params.getMaterialContent());
            if (file != null) {
                logger.info("fileName: " + file.getOriginalFilename());
                try {
                    String url = qiniuCloudUtils.uploadToQiniu(file);
                    logger.info("新图片地址：" + url);
                    material.setPicture(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                material.setPicture(null);
            }
            count2 = materialMapper.insertMaterial(material);
            logger.info("new material: " + material.toString());
        }

        if (count1 > 0) {
            logger.info("新增Audit成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            logger.error("新增Audit失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/audits/result")
    @ApiOperation("通过auditId使管理员新增一个审核结果")
    public ServerResponse addAuditResult(HttpServletRequest request, @Validated AuditResultParam params) {
        logger.info("params: " + params.toString());
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long managerId = Long.parseLong(auth.getUserId());
        AuditResult auditResult = new AuditResult();
        auditResult.setAuditId(params.getAuditId());
        auditResult.setResult(params.getResult());
        auditResult.setManagerId(managerId);
        auditResult.setResultTime(params.getResultTime());
        int count = auditResultMapper.insertAuditResult(auditResult);
        logger.info("new AuditResult: " + auditResult.toString());
        if (count > 0) {
            logger.info("新增AuditResult成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("INSERT SUCCESS, 如通过请调用user的api的修改用户的role");
        } else {
            logger.error("新增AuditResult失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/audits/update")
    @ApiOperation("通过用户ID修改一个审核")
    public ServerResponse updateAudit(HttpServletRequest request,
                                      @Validated AuditParam params,
                                      @RequestPart(required = false) @ApiParam(value = "材料图片") MultipartFile file) {
        logger.info("params: " + params.toString());
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        Long auditId = ArrayUtil.max(auditMapper.findAuditIdsByUserId(userId));
        Audit audit = (Audit) auditMapper.findAuditByAuditId(auditId);
        if (!params.getPhoneNumber().equals(audit.getPhoneNumber())) {
            audit.setPhoneNumber(params.getPhoneNumber());
        }
        if (!params.getIdCard().equals(audit.getIdCard())) {
            audit.setIdCard(params.getIdCard());
        }
        if (!params.getAuditTime().equals(audit.getAuditTime())) {
            audit.setAuditTime(params.getAuditTime());
        }
        if (!params.getRealName().equals(audit.getRealName())) {
            audit.setRealName(params.getRealName());
        }
        int count1 = auditMapper.updateAudit(audit);
        logger.info("update Audit: " + audit.toString());
        int count2 = 0;
        Material material = materialMapper.findMaterialsByAid(auditId);
        if (material != null) {
            if (params.getMaterialContent().equals(material.getMaterialContent())) {
                material.setMaterialContent(params.getMaterialContent());
            }
            if (file != null) {
                logger.info("fileName: " + file.getOriginalFilename());
                try {
                    String url = qiniuCloudUtils.uploadToQiniu(file);
                    String oldUrl = material.getPicture();
                    logger.info("旧图片地址: " + oldUrl);
                    if (oldUrl != null) {
                        int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
                        if (statusCode == -1) {
                            logger.error("旧图片删除失败！");
                        } else {
                            logger.info("旧图片删除成功！");
                        }
                    }
                    material.setPicture(url);
                    logger.info("新图片地址：" + url);
                    logger.info("图片替换成功！");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            count2 = materialMapper.updateMaterial(material);
            logger.info("update material: " + material.toString());
        } else {
            Material material_new = new Material();
            material_new.setAuditId(auditId);
            material_new.setMaterialContent(params.getMaterialContent());
            if (!file.isEmpty()) {
                logger.info("fileName: " + file.getOriginalFilename());
                try {
                    String url = qiniuCloudUtils.uploadToQiniu(file);
                    logger.info("新图片地址：" + url);
                    material_new.setPicture(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                material_new.setPicture(null);
            }
            count2 = materialMapper.insertMaterial(material_new);
            logger.info("update material_new: " + material_new.toString());
        }

        if (count1 > 0) {
            logger.info("更新audit成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("UPDATE SUCCESS!");
        } else {
            logger.error("更新audit失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/audits/updateResult")
    @ApiOperation("通过auditId使管理员修改一个审核结果")
    public ServerResponse updateAuditResult(HttpServletRequest request,
                                            @Validated AuditResultParam params) {
        logger.info("params: " + params.toString());
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long managerId = Long.parseLong(auth.getUserId());
        Long auditId = params.getAuditId();
        AuditResult auditResult = auditResultMapper.findAuditResultByAid(auditId);
        if (!params.getResult().equals(auditResult.getResult())) {
            auditResult.setResult(params.getResult());
        }
        auditResult.setManagerId(managerId);
        if (!params.getResultTime().equals(auditResult.getResultTime())) {
            auditResult.setResultTime(params.getResultTime());
        }
        int count = auditResultMapper.updateAuditResult(auditResult);
        logger.info("update auditResult: " + auditResult.toString());
        if (count > 0) {
            logger.info("更新auditResult成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("UPDATE SUCCESS!");
        } else {
            logger.error("更新auditResult失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/audits/{auditId}")
    @ApiOperation("通过auditId删除一个审核")
    public ServerResponse deleteAudit(@PathVariable @ApiParam(value = "审核ID") String auditId) {
        String msg = "";
        Long id = Long.parseLong(auditId);
        int count1 = auditResultMapper.deleteAuditResult(id);
        if (count1 == 0) {
            msg += "该审核没有结果，";
        } else {
            msg += "该审核有结果，";
        }
        String oldUrl = materialMapper.findPictureByAid(id);
        logger.info("旧图片地址：" + oldUrl);
        if (oldUrl != null) {
            int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
            if (statusCode == -1) {
                logger.info("图片删除失败！");
            } else {
                logger.error("图片删除成功！");
            }
        }
        int count2 = materialMapper.deleteMaterial(id);
        if (count2 == 0) {
            msg += "该审核没有材料，";
        } else {
            msg += "该审核有材料，";
        }
        int count3 = auditMapper.deleteAudit(id);
        logger.info("删除Audit（审核信息、结果、材料） id: " + id);
        if (count3 > 0) {
            msg += "审核删除成功。";
            logger.info(msg);
            logger.info("==========================================");
            return ServerResponse.createBySuccess(msg);
        } else {
            logger.error("删除Audit失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/audits/result/{auditId}")
    @ApiOperation("通过auditId删除一个审核结果")
    public ServerResponse deleteAuditResult(@PathVariable @ApiParam(value = "审核ID") String auditId) {
        Long id = Long.parseLong(auditId);
        int count = auditResultMapper.deleteAuditResult(id);
        logger.info("删除AuditResult id: " + auditId);
        if (count > 0) {
            logger.info("删除AuditResult成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            logger.error("删除AuditResult失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/materials/{auditId}")
    @ApiOperation("通过auditId删除一个审核材料")
    public ServerResponse deleteMaterial(@PathVariable @ApiParam(value = "审核ID") String auditId) {
        Long id = Long.parseLong(auditId);
        int count = materialMapper.deleteMaterial(id);
        logger.info("删除Material id: " + auditId);
        if (count > 0) {
            logger.info("删除Material成功。");
            logger.info("==========================================");
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            logger.error("删除Material失败。");
            logger.info("==========================================");
            return ServerResponse.createByFail();
        }
    }

}
