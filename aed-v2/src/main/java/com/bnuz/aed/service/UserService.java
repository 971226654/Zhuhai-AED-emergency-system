package com.bnuz.aed.service;

/**
 * @author Leia Liang
 */
public interface UserService {

    String getRole(Long userId);

    String getOpenId(Long userId);

    boolean isUserIdExpired(Long userId);

}
