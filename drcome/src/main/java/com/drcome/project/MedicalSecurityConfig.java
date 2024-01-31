package com.drcome.project;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class MedicalSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Bean
    public BCryptPasswordEncoder medicalPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf().disable();

        http.authorizeHttpRequests((requests) -> requests
                .antMatchers("/hospital/**", "/pharmacy/**").permitAll() // Medical에 대한 권한 설정
                .anyRequest().authenticated())

        .formLogin((form) -> form
                .loginPage("/medicallogin")
                .usernameParameter("username")
                .permitAll()
                .loginProcessingUrl("/medicallogin") // POST 요청을 위한 처리 URL 지정
        )
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/") // 로그아웃 성공시 이동할 url
                .invalidateHttpSession(true) // 로그아웃시 생성된 세션 삭제 활성화
                .deleteCookies("JSESSIONID")
                .permitAll();

    }
}
