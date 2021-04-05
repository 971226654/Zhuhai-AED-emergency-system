package com.bnuz.aed.common.config;

import com.bnuz.aed.common.config.interceptors.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
        registration.excludePathPatterns("/login/**", "/v3/**", "/error",
                "/static/**", "/infos/get/**", "/equipments/get", "/equipments/get/**",
                "/refresh",
                "/swagger-ui/**", "/swagger-resources/**", "/webjars/**");
        //registration.excludePathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //其他静态资源
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        //swagger增加url映射
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
