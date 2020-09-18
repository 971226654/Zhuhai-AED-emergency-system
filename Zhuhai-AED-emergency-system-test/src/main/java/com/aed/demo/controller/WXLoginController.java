package com.aed.demo.controller;

import com.aed.demo.util.HttpsClientUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "微信登录接口测试")
public class WxLoginController {

    @PostMapping("/wxLogin")
    @ApiOperation("微信小程序端登录接口测试")
    public String wxLogin(String code) {
        System.out.println("wxLoginCode: " + code);
        String url = "https://api.weixin.qq.com/sns/";
        Map<String, String> params = new HashMap<>();
        params.put("appid", "");
        params.put("secret", "");
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");
        String wxResult = HttpsClientUtil.doGet(url, params);
        System.out.println(wxResult);
        return "got the openid and session_key!";
    }

}
