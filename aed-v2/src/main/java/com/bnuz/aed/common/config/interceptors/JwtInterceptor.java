package com.bnuz.aed.common.config.interceptors;

import com.bnuz.aed.common.tools.utils.JwtTokenUtils;
import com.bnuz.aed.common.tools.ResponseCode;
import com.bnuz.aed.entity.expand.UserAuth;
import com.bnuz.aed.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leia Liang
 */
@Configuration
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("url: " + request.getRequestURI());
        System.out.println("http method: " + request.getMethod());
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        // 判断token是否过期和是否有效
        if (JwtTokenUtils.isTokenExpired(token) && JwtTokenUtils.checkToken(token)) {
            Claims claims = JwtTokenUtils.getClaimsFromToken(token);
            if (claims != null) {
                String res_userId = (String)claims.get("userId");
                String res_role = (String)claims.get("role");
                UserAuth userAuth = new UserAuth();
                userAuth.setUserId(res_userId);
                userAuth.setRole(res_role);
                // 判断该userId是否存在
                if (userServiceImpl.isUserIdExpired(Long.valueOf(res_userId))) {
                    System.out.println("token有效，userId存在");
                    System.out.println(userAuth.toString());
                    request.setAttribute("UserAuth", userAuth);
                    return true;
                }
            }
        } else {
            response.setStatus(ResponseCode.UNAUTHORIZED.getCode());
            System.out.println("token过期或无效，请重新登录");
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}