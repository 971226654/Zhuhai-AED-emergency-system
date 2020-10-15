package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.RealtimeInfoMapper;
import com.bnuz.aed.common.tools.QiniuCloudUtils;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.RealtimeInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "资讯模块接口")
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

    @GetMapping("/infos/{id}")
    @ApiOperation("根据id获取资讯或急救知识")
    public ServerResponse getInfoOrKnow(@PathVariable String id) {
        Long infoId = Long.parseLong(id);
        RealtimeInfo info = realtimeInfoMapper.findInfoOrKnowById(infoId);
        if (info != null) {
            return ServerResponse.createBySuccess(info);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/infos")
    @ApiOperation("新增一条资讯或急救知识")
    public ServerResponse addInfoOrKnow(String releaseTime, String title,
                                        String content, String intro,
                                        String author, int knowledge, int info,
                                        @RequestPart MultipartFile file) {
        RealtimeInfo infoVo = new RealtimeInfo();
        infoVo.setReleaseTime(releaseTime);
        infoVo.setTitle(title);
        infoVo.setContent(content);
        infoVo.setIntro(intro);
        infoVo.setAuthor(author);
        infoVo.setKnowledge(knowledge);
        infoVo.setInfo(info);
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

    @PutMapping("/infos/{id}")
    @ApiOperation("修改一条资讯或急救知识，by 设备ID")
    public ServerResponse updateInfoOrKnow(@PathVariable String id, String releaseTime,
                                           String title, String content, String intro,
                                           String author, int knowledge, int info,
                                           @RequestPart MultipartFile file) {
        Long infoId = Long.parseLong(id);
        RealtimeInfo infoVo = realtimeInfoMapper.findInfoOrKnowById(infoId);
        infoVo.setReleaseTime(releaseTime);
        infoVo.setTitle(title);
        infoVo.setContent(content);
        infoVo.setIntro(intro);
        infoVo.setAuthor(author);
        infoVo.setKnowledge(knowledge);
        infoVo.setInfo(info);
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

    @DeleteMapping("/infos/{id}")
    @ApiOperation("删除一条资讯或急救知识")
    public ServerResponse deleteInfoOrKnow(@PathVariable String id) {
        Long infoId = Long.parseLong(id);
        String oldUrl = realtimeInfoMapper.findMediaById(infoId);
        int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
        if (statusCode == -1) {
            System.out.println("图片删除失败！");
        } else {
            System.out.println("图片删除成功！");
        }
        int count = realtimeInfoMapper.deleteInfoOrKnow(infoId);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }


}
