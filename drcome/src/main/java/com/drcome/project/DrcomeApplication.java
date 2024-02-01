package com.drcome.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@MapperScan(basePackages = "com.drcome.project.**.mapper")
@PropertySource("classpath:myconfig.properties")
public class DrcomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrcomeApplication.class, args);
	}

}
