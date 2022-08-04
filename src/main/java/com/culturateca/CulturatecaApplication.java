package com.culturateca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
@EnableFeignClients
@EnableScheduling
public class CulturatecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CulturatecaApplication.class, args);
	}

}
