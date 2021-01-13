package com.bnuz.aed.common.config.aspect;

import com.bnuz.aed.common.config.AuthConfig;
import com.bnuz.aed.common.tools.ResponseCode;
import com.bnuz.aed.common.tools.utils.CdxException;
import com.bnuz.aed.entity.base.UserAuth;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Leia Liang
 */
@Aspect
@Configuration
public class ControllerAspect {

    /** 不需要登录的方法或登录接口的方法 */
    private final static List<String> passMethod = new ArrayList<>(Arrays.asList(
            "getAllEquipments", "getEquipmentById", "getAllInfos", "getAllKnows",
            "getInfoOrKnow", "createUserByMini", "createUserByWeb", "refreshToken",
            "getWebLoginCodeMap", "getUserInfosFromWeb"));

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

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = null;
        if (attributes != null) {
            request = attributes.getRequest();

            String method = methodSignature.getMethod().getName();
            System.out.println("Method: " + method);

            if (passMethod.contains(method)) {
                System.out.println("get equipments or infos or login.");
                System.out.println("No Need Validation.");
            } else {
                UserAuth userAuth = (UserAuth) request.getAttribute("UserAuth");
                if (userAuth != null) {
                    userAuth.setMethod(method);

                    System.out.println(userAuth.toString());

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
                } else {
                    System.out.println("auth为空，请查看token拦截器");
                    throw new CdxException(ResponseCode.FORBIDDEN.getCode(), "auth为空，请查看token拦截器");
                }
            }
        }

    }

}
