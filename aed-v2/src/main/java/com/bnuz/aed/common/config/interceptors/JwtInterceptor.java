package com.bnuz.aed.common.config.interceptors;

import com.bnuz.aed.common.tools.utils.JwtTokenUtils;
import com.bnuz.aed.common.tools.ResponseCode;
import com.bnuz.aed.entity.base.UserAuth;
import com.bnuz.aed.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author Leia Liang
 */
@Configuration
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        System.out.println("url: " + request.getRequestURI());
        System.out.println("http method: " + request.getMethod());
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String name = headerNames.nextElement();
//            //根据名称获取请求头的值
//            String value = request.getHeader(name);
//            System.out.println("---"+ name + "===" + value);
//        }
        String token = request.getHeader("token");
        System.out.println("token: " + token);
        // 如果进入了拦截器且token为空，则是没有登录
        if (token == null) {
            response.setStatus(ResponseCode.UNAUTHORIZED.getCode());
            response.setHeader("message", "UNAUTHORIZED no token");
            System.out.println("没有token");
            return false;
        }
        // 判断token是否有效
        if (JwtTokenUtils.checkToken(token)) {
            // 判断token是否过期
            if (!JwtTokenUtils.isTokenExpired(token)) {
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
                response.sendError(400, "token过期，请调用token刷新接口");
                System.out.println("token过期，请调用token刷新接口");
            }
        } else {
            response.sendError(400, "token无效请登录");
            System.out.println("token无效，请重新登录");
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
