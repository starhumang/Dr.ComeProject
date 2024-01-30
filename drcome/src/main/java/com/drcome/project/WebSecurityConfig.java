package com.drcome.project;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
		http.authorizeHttpRequests((requests) -> requests
				.antMatchers("/", "/home", "/userjoin").permitAll()
				.antMatchers("/admin/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated())
		
				.formLogin((form) -> form
				.loginPage("/userlogin")
				.usernameParameter("username")
				.successHandler(loginSuccessHandler)
				.failureHandler(failureHandler)
				.permitAll())
				
				.logout((logout) -> logout
				.logoutUrl("/logout")
				.logoutSuccessHandler((request, response, authentication) -> {
					request.getSession().invalidate(); // 세션 무효화
					response.sendRedirect("/"); // 로그아웃 후 리다이렉트할 페이지
				})
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll());

		return http.build();
	}

	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}