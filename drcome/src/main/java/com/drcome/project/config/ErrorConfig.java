package com.drcome.project.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorConfig {
    // 커스텀 에러 페이지
    public void customize(ConfigurableServletWebServerFactory container) {
        ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/error/403");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
        
        container.addErrorPages(error403Page, error500Page);
    }
}
