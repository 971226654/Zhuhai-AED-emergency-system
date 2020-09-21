package com.bnuz.aed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author Leia Liang
 */
@SpringBootApplication
@EnableOpenApi
@EntityScan(basePackages = {"com.bnuz.aed.model"})
@EnableJpaRepositories(basePackages = {"com.bnuz.aed.common.repository"})
public class AedApplication {

    public static void main(String[] args) {
        SpringApplication.run(AedApplication.class, args);
    }

}
