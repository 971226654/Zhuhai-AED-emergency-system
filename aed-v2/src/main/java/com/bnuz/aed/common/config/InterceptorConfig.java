package com.bnuz.aed.common.config;

import com.bnuz.aed.common.config.interceptors.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Leia Liang
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(jwtInterceptor);

        // 拦截配置
        registration.addPathPatterns("/**");

        // 排除配置
        registration.excludePathPatterns("/login/web", "/login/mini");
        //registration.excludePathPatterns("/**");
    }
}
