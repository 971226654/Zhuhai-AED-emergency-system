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

    int insertUser(User user);

    int findUserByName(@Param("userName") String userName);

}
