package com.drcome.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.drcome.project.**.mapper")
public class DrcomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrcomeApplication.class, args);
	}

}
