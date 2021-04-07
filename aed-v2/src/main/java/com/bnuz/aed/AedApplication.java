package com.bnuz.aed;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author Leia Liang
 */
@SpringBootApplication
@EnableOpenApi
@EntityScan(basePackages = {"com.bnuz.aed.model.base"})
@MapperScan(basePackages = {"com.bnuz.aed.common.mapper"})
@EnableWebSocket
public class AedApplication {

    /** 跨域过滤器 */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    public CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(AedApplication.class);
        SpringApplication.run(AedApplication.class, args);
        /* 注意 spring 默认日志输出级别为 info 所以默认情况下 这句不会打印到控制台 */
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
    }

}
