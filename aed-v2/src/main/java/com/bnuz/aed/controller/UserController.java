package com.bnuz.aed.controller;

import com.bnuz.aed.common.mapper.UserMapper;
import com.bnuz.aed.common.tools.ResponseCode;
import com.bnuz.aed.common.tools.ServerResponse;
import com.bnuz.aed.entity.base.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "用户模块接口")
public class UserController {

    @Autowired
    private UserMapper userMapper;

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

    //TODO: 不返回userId
    @PostMapping("/users")
    @ApiOperation("创建一个用户,返回该用户id")
    public ServerResponse createUser(@RequestParam String name, @RequestParam String openid) {
        User user = new User();
        user.setUserName(name);
        user.setWxOpenid(openid);
        int count  = userMapper.insertUser(user);
        int userId = userMapper.findUserByName(name);
        if (userId > 0 && count > 0) {
            return ServerResponse.createByFreeStyle(ResponseCode.CREATED.getCode(),
                    ResponseCode.CREATED.getDesc(), userId);
        } else {
            return ServerResponse.createByFail();
        }
    }

    
// TODO: 用Token保存userId进行前端的请求

//    @PutMapping("/users/{id}")
//    @ApiOperation("修改某个一个用户的信息")
//    public ServerResponse updateUser(@PathVariable String id) {
//        BeanUtils.copyProperties();
//    }


}
