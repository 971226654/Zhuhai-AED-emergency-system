package com.bnuz.aed.common.mapper;

import com.bnuz.aed.entity.base.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Leia Liang
 */
@Mapper
public interface UserMapper {

    List<User> findAllUsers();

    int insertUserByOpenidAndRole(User user);

    int insertUserByOpenidAndRoleAndName(User user);

    User findUserByOpenid(@Param("openid") String openid);

    User findUserByUserId(@Param("userId") Long userId);

    List<User> findAllInspectors();

    List<User> findAllManagers();

    String findUserRole(@Param("userId") Long userId);

    String findUserOpenid(@Param("userId") Long userId);

    int checkUserExpired(@Param("userId") Long userId);

    int updateUserInfoByUserId(User user);

    int deleteUserByUserId(@Param("userId") Long userId);

}
