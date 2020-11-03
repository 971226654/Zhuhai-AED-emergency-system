package com.bnuz.aed.service.impl;

import com.bnuz.aed.common.mapper.UserMapper;
import com.bnuz.aed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leia Liang
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public String getRole(Long userId) {
        return userMapper.findUserRole(userId);
    }

    @Override
    public String getOpenId(Long userId) {
        return userMapper.findUserOpenid(userId);
    }

    @Override
    public boolean isUserIdExpired(Long userId) {
        return userMapper.checkUserExpired(userId) == 1;
    }
}
