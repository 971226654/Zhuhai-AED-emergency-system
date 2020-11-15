package com.bnuz.aed.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bnuz.aed.common.mapper.UserMapper;
import com.bnuz.aed.common.tools.utils.JwtTokenUtils;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.common.tools.utils.WechatUtils;
import com.bnuz.aed.entity.base.User;
import com.bnuz.aed.entity.expand.UserAuth;
import com.bnuz.aed.entity.expand.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "UserController", description = "用户模块接口")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    private final static List<String> wxErrCode = ListUtil.toList("40029", "40030");

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

    @PostMapping("/login/mini")
    @ApiOperation("小程序创建一个用户,返回该用户id、role封装好的token")
    public ServerResponse createUserByMini(@RequestBody String codeJson) {
        JSONObject code = JSONUtil.parseObj(codeJson);
        System.out.println("code: " + code.getStr("code"));
        JSONObject key = WechatUtils.getOpenIdByMini(code.getStr("code"));
        System.out.println(key);
//        return ServerResponse.createBySuccess(key);
        if (wxErrCode.contains(key.getStr("errcode"))) {
            return ServerResponse.createByFail(key.getStr("errmsg"));
        }
        String openid = key.getStr("openid");
        User check_user = userMapper.findUserByOpenid(openid);
        int insert_count;
        Long userId;
        String role;
        // 判断数据库是否有这个openid,有就不注册(插入数据库)
        if (check_user == null) {
            User new_user = new User();
            new_user.setWxOpenid(openid);
            new_user.setRole("USER");
            insert_count = userMapper.insertUser(new_user);
            userId = userMapper.findUserByOpenid(openid).getUserId();
            role = "USER";
        } else {
            insert_count = -1;
            userId = check_user.getUserId();
            role = check_user.getRole();
        }
        if (insert_count > 0) {
            return ServerResponse.createBySuccess("注册并登录成功！返回token",
                    JwtTokenUtils.generateToken(String.valueOf(userId), role));
        } else if (insert_count == -1){
            return ServerResponse.createBySuccess("登录成功！返回token",
            JwtTokenUtils.generateToken(String.valueOf(userId), role));
        } else {
            return ServerResponse.createByFail("请检查code！");
        }

    }

    @PostMapping("/login/web")
    @ApiOperation("web创建一个用户，返回该用户id、role封装好的token，该方法用于普通用户想成为安全员必须先登录")
    public ServerResponse createUserByWeb(@RequestBody String codeJson) {
        JSONObject code = JSONUtil.parseObj(codeJson);
        System.out.println("code: " + code.getStr("code"));
        JSONObject access = WechatUtils.getAccessTokenByWeb(code.getStr("code"));
        System.out.println(access);
        if (wxErrCode.contains(access.getStr("errcode"))) {
            return ServerResponse.createByFail(access.getStr("errmsg"));
        }
        String accessToken = access.getStr("access_token");
        String openid = access.getStr("openid");
        JSONObject info = WechatUtils.getInfoByWeb(accessToken, openid);
        String nickName = info.getStr("nickname");
        int insert_count;
        Long userId;
        String role;
        User check_user = userMapper.findUserByOpenid(openid);
        // 判断数据库是否有这个openid,有就不注册(插入数据库)
        if (check_user == null) {
            User new_user = new User();
            new_user.setWxOpenid(openid);
            new_user.setUserName(nickName);
            new_user.setRole("USER");
            insert_count = userMapper.insertUser(new_user);
            userId = userMapper.findUserByOpenid(openid).getUserId();
            role = "USER";
        } else {
            insert_count = -1;
            userId = check_user.getUserId();
            role = check_user.getRole();
        }

        if (insert_count > 0) {
            return ServerResponse.createBySuccess("注册并登录成功！返回token",
                    JwtTokenUtils.generateToken(String.valueOf(userId), role));
        } else if (insert_count == -1){
            return ServerResponse.createBySuccess("登录成功！返回token",
                    JwtTokenUtils.generateToken(String.valueOf(userId), role));
        } else {
            return ServerResponse.createByFail("请检查code！");
        }

    }


    @PutMapping("/users")
    @ApiOperation("修改某个一个用户的信息,暂时不要用")
    public ServerResponse updateUser(HttpServletRequest request, @RequestBody UserInfo info) {
        UserAuth auth = (UserAuth) request.getAttribute("UserAuth");
        Long userId = Long.parseLong(auth.getUserId());
        User user = userMapper.findUserByUserId(userId);
        user.setUserName(info.getUserName());
        user.setPhoneNumber(info.getPhoneNumber());
        user.setEmail(info.getEmail());
        user.setIdCard(info.getIdCard());
        int count = userMapper.updateUserInfoByUserId(user);
        if (count > 0) {
            return ServerResponse.createBySuccess("UPDATE SUCCESS!");
        } else {
            return ServerResponse.createByFail();
        }
    }

    @DeleteMapping("/users/{userId}")
    @ApiOperation("删除一个用户")
    public ServerResponse deleteUser(@PathVariable String userId) {
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
