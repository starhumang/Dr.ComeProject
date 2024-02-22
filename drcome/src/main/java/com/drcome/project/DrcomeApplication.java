package com.drcome.project;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import com.drcome.project.common.domain.Detailcode;
import com.drcome.project.common.repository.DetailCodeRepository;
import com.drcome.project.common.service.DetailCodeService;

@SpringBootApplication
@MapperScan(basePackages = "com.drcome.project.**.mapper")
@PropertySource("classpath:myconfig.properties")
public class DrcomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrcomeApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner detail(DetailCodeRepository repository, DetailCodeService detailCodeService, ServletContext sc) {
	    return (args) -> {
	        List<Detailcode> allDetailCodes = repository.findAll();
	        Map<String, List<Detailcode>> alphabetGroups = detailCodeService.getAlphabetGroups();

	        // 전체 detailCode 저장
	        sc.setAttribute("detailCode", allDetailCodes);
//	        System.out.println(allDetailCodes);

	        // 알파벳으로 그룹화된 detailCode 저장
	        alphabetGroups.forEach((key, value) -> {
	        	sc.setAttribute("detailCode_" + key, value);
	        });
	    };
	}

}
