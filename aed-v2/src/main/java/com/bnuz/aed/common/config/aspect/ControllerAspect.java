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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    /** 不需要登录的方法或登录接口的方法 */
    private final static List<String> passMethod = new ArrayList<>(Arrays.asList(
            "getAllEquipments", "getEquipmentById", "getAllInfos", "getAllKnows",
            "getInfoOrKnow", "createUserByMini", "createUserByWeb", "refreshToken",
            "getWebLoginCodeMap", "getUserInfosFromWeb", "hello", "loginByPassword", "log"));

    @Autowired
    private AuthConfig authConfig;

    /** 定义切点Pointcut */
    @Pointcut("execution(* com.bnuz.aed.controller..*.*(..))")
    public void controllerValidationPoint() {

    }

    @Before("controllerValidationPoint()")
    public void doBefore(JoinPoint joinPoint) {

        logger.info(joinPoint.toString());
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = null;
        if (attributes != null) {
            request = attributes.getRequest();

            String method = methodSignature.getMethod().getName();
            logger.info("Method: " + method);

            if (passMethod.contains(method)) {
                logger.info("get equipments or infos or login.");
                logger.info("No Need Validation.");
            } else {
                UserAuth userAuth = (UserAuth) request.getAttribute("UserAuth");
                if (userAuth != null) {
                    userAuth.setMethod(method);
                    logger.info(userAuth.toString());
                    String role = userAuth.getRole();
                    if ("MANAGER".equals(role)) {
                        if (authConfig.getManager().contains(method)) {
                            request.setAttribute("UserAuth", userAuth);
                            logger.info("Pass Validation.");
                        } else {
                            logger.error("No Validation.");
                            throw new CdxException(ResponseCode.FORBIDDEN.getCode(), "No Validation.");
                        }
                    } else if ("INSPECTOR".equals(role)) {
                        if (authConfig.getInspector().contains(method)) {
                            request.setAttribute("UserAuth", userAuth);
                            logger.info("Pass Validation.");
                        } else {
                            logger.error("No Validation.");
                            throw new CdxException(ResponseCode.FORBIDDEN.getCode(), "No Validation.");
                        }
                    } else if ("USER".equals(role)) {
                        if (authConfig.getUser().contains(method)) {
                            request.setAttribute("UserAuth", userAuth);
                            logger.info("Pass Validation.");
                        } else {
                            logger.error("No Validation.");
                            throw new CdxException(ResponseCode.FORBIDDEN.getCode(), "No Validation.");
                        }
                    } else {
                        logger.error("角色错误，请检查数据库");
                        throw new CdxException(ResponseCode.FORBIDDEN.getCode(), "角色错误，请检查数据库");
                    }
                } else {
                    logger.error("auth为空，请查看token拦截器");
                    throw new CdxException(ResponseCode.FORBIDDEN.getCode(), "auth为空，请查看token拦截器");
                }
            }
        }
    }
}
