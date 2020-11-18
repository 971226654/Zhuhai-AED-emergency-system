package com.bnuz.aed.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bnuz.aed.common.mapper.UserMapper;
import com.bnuz.aed.common.tools.utils.JwtTokenUtils;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.common.tools.utils.WechatUtils;
import com.bnuz.aed.entity.base.User;
import com.bnuz.aed.entity.base.UserAuth;
import com.bnuz.aed.entity.expand.UserOutput;
import com.bnuz.aed.entity.params.RefreshTokenParam;
import com.bnuz.aed.entity.params.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leia Liang
 */
@RestController
@ResponseBody
@Api(tags = "UserController", description = "用户模块接口")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    private final static List<String> wxErrCode = ListUtil.toList("40029", "40030");

    private final static List<String> wxCodeErrCode = ListUtil.toList("10003",
            "10004", "10005", "10006", "10009", "10010", "10011", "10012", "10013",
            "10015", "10016");

    private static String OPENID = "";

    private static String ACCESS_TOKEN = "";

    @GetMapping("/users")
    @ApiOperation("获取所有用户信息列表")
    public ServerResponse getAllUsers() {
        List<User> users = userMapper.findAllUsers();
        if (users != null) {
            return ServerResponse.createBySuccess(users);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @PostMapping("/refresh")
    @ApiOperation("刷新Token")
    public ServerResponse refreshToken(@RequestBody RefreshTokenParam param) {
        System.out.println("RefreshTokenParam: " + param.toString());
        String new_token = JwtTokenUtils.generateToken(
                String.valueOf(param.getUserId()),
                param.getRole());
        System.out.println("new_token: " + new_token);
        return ServerResponse.createBySuccess("返回新的token", new_token);
    }

    @PostMapping("/login/mini")
    @ApiOperation("小程序创建一个用户,返回该用户id、role封装好的token")
    public ServerResponse createUserByMini(@RequestBody String codeJson) {
        System.out.println("codeJson: " + codeJson);
        JSONObject code = JSONUtil.parseObj(codeJson);
        System.out.println("code: " + code.getStr("code"));
        JSONObject key = WechatUtils.getOpenIdByMini(code.getStr("code"));
        System.out.println(key);
//        return ServerResponse.createBySuccess(key);
        if (wxErrCode.contains(key.getStr("errcode"))) {
            return ServerResponse.createByFail(key.getStr("errmsg"));
        }
        OPENID = key.getStr("openid");
        User check_user = userMapper.findUserByOpenid(OPENID);
        UserOutput output;
        int insert_count;
        // 判断数据库是否有这个openid,有就不注册(插入数据库)
        if (check_user == null) {
            output = new UserOutput();
            User new_user = new User();
            new_user.setWxOpenid(OPENID);
            new_user.setRole("USER");
            insert_count = userMapper.insertUserByOpenidAndRole(new_user);
            Long userId = userMapper.findUserByOpenid(OPENID).getUserId();
            String role = "USER";
            String token = JwtTokenUtils.generateToken(String.valueOf(userId), role);
            System.out.println("mini-token: " + token);
            output.setUserId(userId);
            output.setWxOpenid(OPENID);
            output.setRole(role);
            output.setToken(token);
        } else {
            insert_count = -1;
            output = new UserOutput(check_user);
            String token = JwtTokenUtils.generateToken(String.valueOf(output.getUserId()), output.getRole());
            System.out.println("mini-token: " + token);
            output.setToken(token);
        }

        if (insert_count > 0) {
            return ServerResponse.createBySuccess("注册并登录成功！返回token", output);
        } else if (insert_count == -1){
            return ServerResponse.createBySuccess("登录成功！返回token", output);
        } else {
            return ServerResponse.createByFail("请检查code！");
        }

    }

    @PostMapping("/login/getCodeMap")
    @ApiOperation("web请求登录授权获得code用")
    public ServerResponse getWebLoginCodeMap(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        System.out.println("Login SessionId: " + sessionId);
        String uri = WechatUtils.getCode(sessionId);
        System.out.println("getCodeURI: " + uri);
        Map<String, String> map = new HashMap<>();
        map.put("sessionId", sessionId);
        map.put("uri", uri);
        return ServerResponse.createBySuccess("获取成功", map);
    }

    @RequestMapping(value = "/login/callback")
    @ApiOperation("微信网页第三方回调函数,不要调用")
    public ServerResponse createUserByWeb(HttpServletRequest request) {
        String state = request.getParameter("state");
        if (wxCodeErrCode.contains(state)) {
            return ServerResponse.createByFail("code获取出错，errCode: " + state);
        }
        String code = request.getParameter("code");
        System.out.println("code: " + code);
        JSONObject access = WechatUtils.getAccessTokenByWeb(code);
        System.out.println("access: " + access);
        if (wxErrCode.contains(access.getStr("errcode"))) {
            return ServerResponse.createByFail(access.getStr("errmsg"));
        }

        ACCESS_TOKEN = access.getStr("access_token");
        OPENID = access.getStr("openid");

        int insert_count;
        User check_user = userMapper.findUserByOpenid(OPENID);
        // 判断数据库是否有这个openid,有就不注册(插入数据库)
        if (check_user == null) {
            User new_user = new User();
            new_user.setWxOpenid(OPENID);
            new_user.setRole("USER");
            insert_count = userMapper.insertUserByOpenidAndRole(new_user);
        } else {
            insert_count = -1;
        }

        if (insert_count > 0) {
            return ServerResponse.createBySuccess("注册并登录成功!");
        } else if (insert_count == -1){
            return ServerResponse.createBySuccess("登录成功!");
        } else {
            return ServerResponse.createByFail("请检查code！");
        }

    }

    @GetMapping("/login/web")
    @ApiOperation("web第三方扫码后调用")
    public ServerResponse getUserInfosFromWeb() {
        JSONObject info = WechatUtils.getInfoByWeb(ACCESS_TOKEN, OPENID);
        User check_user = userMapper.findUserByOpenid(OPENID);
        UserOutput output = new UserOutput(check_user);
        String token = JwtTokenUtils.generateToken(String.valueOf(output.getUserId()), output.getRole());
        System.out.println("web-token: " + token);
        output.setToken(token);
        Map<String, Object> map = new HashMap<>();
        map.put("UserOutput", output);
        map.put("WechatInfo", info);
        ACCESS_TOKEN = "";
        OPENID = "";
        return ServerResponse.createBySuccess("返回扫码后登录信息", map);
    }

    @PutMapping("/users")
    @ApiOperation("修改某个一个用户的信息")
    public ServerResponse updateUser(HttpServletRequest request, @Validated @RequestBody UserInfo info) {
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        User user = userMapper.findUserByUserId(userId);
        user.setUserName(info.getUserName());
        user.setPhoneNumber(info.getPhoneNumber());
        user.setEmail(info.getEmail());
        user.setIdCard(info.getIdCard());
        user.setResponsibleArea(info.getResponsibleArea());
        user.setRole(info.getRole());
        int count = userMapper.updateUserInfoByUserId(user);
        if (count > 0) {
            return ServerResponse.createBySuccess("UPDATE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/users/{userId}")
    @ApiOperation("删除一个用户")
    public ServerResponse deleteUser(@PathVariable @ApiParam(value = "用户ID") String userId) {
        Long id = Long.parseLong(userId);
        int count = userMapper.deleteUserByUserId(id);
        if (count > 0) {
            return ServerResponse.createBySuccess("DELETE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/inspectors")
    @ApiOperation("查找目前所有检查员信息")
    public ServerResponse getAllInspectors() {
        List<User> inspectors = userMapper.findAllInspectors();
        if (inspectors != null) {
            return ServerResponse.createBySuccess(inspectors);
        } else {
            return ServerResponse.createByFail();
        }
    }

    @GetMapping("/inspectors/{inspectorId}")
    @ApiOperation("查找该id的检查员信息")
    public ServerResponse getInspector(@PathVariable String inspectorId) {
        Long id = Long.parseLong(inspectorId);
        User inspector = userMapper.findUserByUserId(id);
        String msg;
        if ("INSPECTOR".equals(inspector.getRole())) {
            msg = "该ID为检查员，信息在data";
            return ServerResponse.createBySuccess(msg, inspector);
        } else {
            msg = "该ID不是检查员";
            return ServerResponse.createByFail(msg);
        }
    }

    @GetMapping("/managers")
    @ApiOperation("查找目前所有管理员信息")
    public ServerResponse getAllManagers() {
        List<User> managers = userMapper.findAllManagers();
        if (managers != null) {
            return ServerResponse.createBySuccess(managers);
        } else {
            return ServerResponse.createByFail();
        }
    }

}
