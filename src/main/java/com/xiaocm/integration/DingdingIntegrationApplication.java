package com.xiaocm.integration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaocm.integration.sync.mapper")
public class DingdingIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DingdingIntegrationApplication.class, args);
		
	}

}
