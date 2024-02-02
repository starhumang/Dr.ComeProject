package com.drcome.project;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final LoginSuccessHandler loginSuccessHandler;
    private final FailureHandler failureHandler;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public WebSecurityConfig(LoginSuccessHandler loginSuccessHandler, FailureHandler failureHandler,
                             CustomAuthenticationProvider customAuthenticationProvider) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.failureHandler = failureHandler;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests((requests) -> requests
				.antMatchers("/**").permitAll()
				//.antMatchers("/", "/home", "/userjoin").permitAll() // 나중에 이걸로 바꿔야함
				//.antMatchers("/admin/**").hasAnyRole("ADMIN") // 얘도
				.anyRequest().authenticated())
		
				.formLogin((form) -> form
				.loginPage("/userlogin")
				.usernameParameter("username")
				.successHandler(loginSuccessHandler)
				.failureHandler(failureHandler)
				.permitAll())
				
				.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/") //로그아웃 성공시 이동할 url
                .invalidateHttpSession(true) //로그아웃시 생성된 세션 삭제 활성화
                .deleteCookies("JSESSIONID")
                .permitAll();

		return http.build();
	}

	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}