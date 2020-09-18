package org.bnuz.aedweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author Leia Liang
 */
@SpringBootApplication
@EnableOpenApi
public class AedWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AedWebApplication.class, args);
	}

}
