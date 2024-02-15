package com.drcome.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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
public class UserSecurityConfig {

	private final LoginSuccessHandler loginSuccessHandler;
	private final FailureHandler failureHandler;

	@Autowired
	public UserSecurityConfig(LoginSuccessHandler loginSuccessHandler, FailureHandler failureHandler) {
		this.loginSuccessHandler = loginSuccessHandler;
		this.failureHandler = failureHandler;
	}

	@Bean
	public BCryptPasswordEncoder userPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.authorizeHttpRequests((requests) -> requests
//				.antMatchers("/**").permitAll()
				.antMatchers("/", "/home", "/userlogin", "/userjoin", "/medicaljoin", "/findAccount", "/auth/**", "/echo").permitAll() // 나중에
																															// 이걸로
																															// 바꿔야함
				.antMatchers("/admin/**").hasAnyRole("ADMIN") // 얘도
				.antMatchers("/hospital/**").hasAnyRole("HOSPITAL") // 얘도
				.antMatchers("/pharmacy/**").hasAnyRole("PHARMACY") // 얘도
				.anyRequest().authenticated())

				.formLogin((form) -> form.loginPage("/userlogin").usernameParameter("username")
						.successHandler(loginSuccessHandler).failureHandler(failureHandler).permitAll())

				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/") // 로그아웃 성공시
																											// 이동할 url
				.invalidateHttpSession(true) // 로그아웃시 생성된 세션 삭제 활성화
				.deleteCookies("JSESSIONID").permitAll();

		return http.build();
	}

	// static 접근 허용
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

}