package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.RealtimeInfoMapper;
import com.bnuz.aed.common.tools.utils.QiniuCloudUtils;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.RealtimeInfo;
import com.bnuz.aed.entity.base.UserAuth;
import com.bnuz.aed.entity.params.RealtimeInfoPostParam;
import com.bnuz.aed.entity.params.RealtimeInfoPutParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author Leia Liang
 */
@RestController
@ResponseBody
@Api(tags = "RealtimeInfoController", description = "资讯模块接口")
public class RealtimeInfoController {

    @Autowired
    private RealtimeInfoMapper realtimeInfoMapper;

    private QiniuCloudUtils qiniuCloudUtils = new QiniuCloudUtils();

    @GetMapping("/infos/get/info")
    @ApiOperation("获取所有资讯")
    public ServerResponse getAllInfos() {
        List<RealtimeInfo> infos = realtimeInfoMapper.findAllInfos();
        if (infos != null) {
            return ServerResponse.createBySuccess(infos);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/infos/get/know")
    @ApiOperation("获取所有急救知识")
    public ServerResponse getAllKnows() {
        List<RealtimeInfo> knows = realtimeInfoMapper.findAllKnows();
        if (knows != null) {
            return ServerResponse.createBySuccess(knows);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/infos/get/{infoId}")
    @ApiOperation("根据id获取资讯或急救知识")
    public ServerResponse getInfoOrKnow(@PathVariable @ApiParam(value = "资讯知识ID") String infoId) {
        Long id = Long.parseLong(infoId);
        RealtimeInfo info = realtimeInfoMapper.findInfoOrKnowById(id);
        if (info != null) {
            return ServerResponse.createBySuccess(info);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/infos/post")
    @ApiOperation("新增一条资讯或急救知识")
    public ServerResponse addInfoOrKnow(HttpServletRequest request, @Validated RealtimeInfoPostParam params,
                                        @RequestPart(required = false) @ApiParam(value = "资讯图片") MultipartFile file) {
        System.out.println(params.toString());
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        System.out.println(auth.toString());
        RealtimeInfo infoVo = new RealtimeInfo();
        infoVo.setReleaseTime(params.getReleaseTime());
        infoVo.setTitle(params.getTitle());
        infoVo.setContent(params.getContent());
        infoVo.setIntro(params.getIntro());
        infoVo.setAuthor(params.getAuthor());
        infoVo.setKnowledge(Integer.parseInt(params.getKnowledge()));
        infoVo.setInfo(Integer.parseInt(params.getInfo()));
        if (file != null) {
            System.out.println(file.getOriginalFilename());
            try {
                String url = qiniuCloudUtils.uploadToQiniu(file);
                System.out.println("新图片地址：" + url);
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

    @PostMapping("/infos/update")
    @ApiOperation("修改一条资讯或急救知识")
    public ServerResponse updateInfoOrKnow(HttpServletRequest request, @Validated RealtimeInfoPutParam params,
                                           @RequestPart(required = false) @ApiParam(value = "资讯图片") MultipartFile file) {
        System.out.println(params.toString());
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        System.out.println(auth.toString());
        Long infoId = Long.valueOf(params.getInfoId());
        RealtimeInfo infoVo = realtimeInfoMapper.findInfoOrKnowById(infoId);
        if (!params.getReleaseTime().equals(infoVo.getReleaseTime())) {
            infoVo.setReleaseTime(params.getReleaseTime());
        }
        if (!params.getTitle().equals(infoVo.getTitle())) {
            infoVo.setTitle(params.getTitle());
        }
        if (!params.getContent().equals(infoVo.getContent())) {
            infoVo.setContent(params.getContent());
        }
        if (!params.getIntro().equals(infoVo.getIntro())) {
            infoVo.setIntro(params.getIntro());
        }
        if (!params.getAuthor().equals(infoVo.getAuthor())) {
            infoVo.setAuthor(params.getAuthor());
        }
        if (!params.getKnowledge().equals(String.valueOf(infoVo.getKnowledge()))) {
            infoVo.setKnowledge(Integer.parseInt(params.getKnowledge()));
        }
        if (!params.getInfo().equals(String.valueOf(infoVo.getInfo()))) {
            infoVo.setInfo(Integer.parseInt(params.getInfo()));
        }

        if (file != null) {
            System.out.println(file.getOriginalFilename());
            try {
                String url = qiniuCloudUtils.uploadToQiniu(file);
                String oldUrl = realtimeInfoMapper.findMediaById(infoId);
                System.out.println("旧图片地址：" + oldUrl);
                if (oldUrl != null) {
                    int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
                    if (statusCode == -1) {
                        System.out.println("图片删除失败！");
                    } else {
                        System.out.println("图片删除成功！");
                    }
                }
                infoVo.setMedia(url);
                System.out.println("图片替换成功！");
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

    @DeleteMapping("/infos/delete/{infoId}")
    @ApiOperation("删除一条资讯或急救知识")
    public ServerResponse deleteInfoOrKnow(@PathVariable @ApiParam(value = "资讯知识ID") String infoId) {
        Long id = Long.parseLong(infoId);
        String oldUrl = realtimeInfoMapper.findMediaById(id);
        System.out.println("旧图片地址：" + oldUrl);
        if (oldUrl != null ) {
            int statusCode = qiniuCloudUtils.deleteFromQiniu(oldUrl);
            if (statusCode == -1) {
                System.out.println("图片删除失败！");
            } else {
                System.out.println("图片删除成功！");
            }
        }
        int count = realtimeInfoMapper.deleteInfoOrKnow(id);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }


}
