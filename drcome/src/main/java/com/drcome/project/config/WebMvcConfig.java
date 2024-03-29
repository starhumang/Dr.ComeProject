package com.drcome.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Value("${file.loading.path}")
	private String loadingPath;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// window
		//registry.addResourceHandler("/img/**").addResourceLocations(loadingPath);
		
		// linux
		registry.addResourceHandler("/img/**").addResourceLocations("file:///" + loadingPath);
	}

}
