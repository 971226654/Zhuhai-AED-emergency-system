package com.bnuz.aed.common.config.aspect;

import com.bnuz.aed.common.config.AuthConfig;
import com.bnuz.aed.common.tools.ResponseCode;
import com.bnuz.aed.common.tools.utils.CdxException;
import com.bnuz.aed.entity.expand.UserAuth;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leia Liang
 */
@Aspect
@Configuration
public class ControllerAspect {

    @Autowired
    private AuthConfig authConfig;

    /** 定义切点Pointcut */
    @Pointcut("execution(* com.bnuz.aed.controller..*.*(..))")
    public void controllerValidationPoint() {

    }

    @Before("controllerValidationPoint()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println(joinPoint.toString());
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

//        Object[] args = joinPoint.getArgs();
//        HttpServletRequest request = (HttpServletRequest) args[0];

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = null;
        if (attributes != null) {
            request = attributes.getRequest();
            UserAuth userAuth = null;
            userAuth = (UserAuth) request.getAttribute("UserAuth");

            String method = methodSignature.getMethod().getName();
            if (userAuth != null) {
                userAuth.setMethod(method);
                System.out.println(userAuth.toString());
            }

            String role = userAuth.getRole();
            if ("MANAGER".equals(role)) {
                if (authConfig.getManager().contains(method)) {
                    request.setAttribute("UserAuth", userAuth);
                    System.out.println("Pass Validation.");
                } else {
                    System.out.println("No Validation.");
                    throw new CdxException(ResponseCode.FORBIDDEN.getCode(), "No Validation.");
                }
            } else if ("INSPECTOR".equals(role)) {
                if (authConfig.getInspector().contains(method)) {
                    request.setAttribute("UserAuth", userAuth);
                    System.out.println("Pass Validation.");
                } else {
                    System.out.println("No Validation.");
                    throw new CdxException(ResponseCode.FORBIDDEN.getCode(), "No Validation.");
                }
            } else if ("USER".equals(role)) {
                if (authConfig.getUser().contains(method)) {
                    request.setAttribute("UserAuth", userAuth);
                    System.out.println("Pass Validation.");
                } else {
                    System.out.println("No Validation.");
                    throw new CdxException(ResponseCode.FORBIDDEN.getCode(), "No Validation.");
                }
            } else {
                System.out.println("角色错误，请检查数据库");
                throw new CdxException(ResponseCode.FORBIDDEN.getCode(), "角色错误，请检查数据库");
            }
        }

    }


}
