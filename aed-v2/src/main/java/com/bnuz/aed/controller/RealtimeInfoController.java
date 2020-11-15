package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.RealtimeInfoMapper;
import com.bnuz.aed.common.tools.utils.QiniuCloudUtils;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.RealtimeInfo;
import com.bnuz.aed.entity.params.RealtimeInfoParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "RealtimeInfoController", description = "资讯模块接口")
public class RealtimeInfoController {

    @Autowired
    private RealtimeInfoMapper realtimeInfoMapper;

    private QiniuCloudUtils qiniuCloudUtils = new QiniuCloudUtils();

    @GetMapping("/infos/info")
    @ApiOperation("获取所有资讯")
    public ServerResponse getAllInfos() {
        List<RealtimeInfo> infos = realtimeInfoMapper.findAllInfos();
        if (infos != null) {
            return ServerResponse.createBySuccess(infos);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/infos/know")
    @ApiOperation("获取所有急救知识")
    public ServerResponse getAllKnows() {
        List<RealtimeInfo> knows = realtimeInfoMapper.findAllKnows();
        if (knows != null) {
            return ServerResponse.createBySuccess(knows);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/infos/{infoId}")
    @ApiOperation("根据id获取资讯或急救知识")
    public ServerResponse getInfoOrKnow(@PathVariable String infoId) {
        Long id = Long.parseLong(infoId);
        RealtimeInfo info = realtimeInfoMapper.findInfoOrKnowById(id);
        if (info != null) {
            return ServerResponse.createBySuccess(info);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/infos")
    @ApiOperation("新增一条资讯或急救知识")
    public ServerResponse addInfoOrKnow(@Validated RealtimeInfoParam params,
                                        @RequestPart MultipartFile file) {
        System.out.println(params.toString());
        RealtimeInfo infoVo = new RealtimeInfo();
        infoVo.setReleaseTime(params.getReleaseTime());
        infoVo.setTitle(params.getTitle());
        infoVo.setContent(params.getContent());
        infoVo.setIntro(params.getIntro());
        infoVo.setAuthor(params.getAuthor());
        infoVo.setKnowledge(params.getKnowledge());
        infoVo.setInfo(params.getInfo());
        if (!file.isEmpty()) {
            System.out.println(file.getOriginalFilename());
            try {
                String url = qiniuCloudUtils.uploadToQiniu(file);
                infoVo.setMedia(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            infoVo.setMedia(null);
        }

        int count = realtimeInfoMapper.insertInfoOrKnow(infoVo);
        if (count > 0) {
            return ServerResponse.createBySuccess("INSERT SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PutMapping("/infos")
    @ApiOperation("修改一条资讯或急救知识")
    public ServerResponse updateInfoOrKnow(@Validated RealtimeInfo params,
                                           @RequestPart MultipartFile file) {
        System.out.println(params.toString());
        Long infoId = params.getInfoId();
        RealtimeInfo infoVo = realtimeInfoMapper.findInfoOrKnowById(infoId);
        infoVo.setReleaseTime(params.getReleaseTime());
        infoVo.setTitle(params.getTitle());
        infoVo.setContent(params.getContent());
        infoVo.setIntro(params.getIntro());
        infoVo.setAuthor(params.getAuthor());
        infoVo.setKnowledge(params.getKnowledge());
        infoVo.setInfo(params.getInfo());
        if (!file.isEmpty()) {
            System.out.println(file.getOriginalFilename());
            try {
                String url = qiniuCloudUtils.uploadToQiniu(file);
                String oldUrl = realtimeInfoMapper.findMediaById(infoId);
                int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
                if (statusCode == -1) {
                    System.out.println("图片删除失败！");
                } else {
                    System.out.println("图片删除成功！");
                }
                infoVo.setMedia(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int count = realtimeInfoMapper.updateInfoOrKnow(infoVo);
        if (count > 0) {
            return ServerResponse.createBySuccess("UPDATE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/infos/{infoId}")
    @ApiOperation("删除一条资讯或急救知识")
    public ServerResponse deleteInfoOrKnow(@PathVariable String infoId) {
        Long id = Long.parseLong(infoId);
        String oldUrl = realtimeInfoMapper.findMediaById(id);
        int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
        if (statusCode == -1) {
            System.out.println("图片删除失败！");
        } else {
            System.out.println("图片删除成功！");
        }
        int count = realtimeInfoMapper.deleteInfoOrKnow(id);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }


}
