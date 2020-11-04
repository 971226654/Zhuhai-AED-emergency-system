package com.bnuz.aed.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leia Liang
 */
@Data
@Component
// 不同的配置类，其前缀不能相同
@ConfigurationProperties(prefix = "auth")
// 必须标明这个类是允许配置的
@EnableConfigurationProperties(AuthConfig.class)
public class AuthConfig {

    private List<String> manager = new ArrayList<>();

    private List<String> inspector = new ArrayList<>();

    private List<String> user = new ArrayList<>();

}
