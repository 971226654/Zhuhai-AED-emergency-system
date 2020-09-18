package com.bnuz.aed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author Leia Liang
 */
@SpringBootApplication
@EnableOpenApi
public class AedApplication {

    public static void main(String[] args) {
        SpringApplication.run(AedApplication.class, args);
    }

}
