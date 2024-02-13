package com.drcome.project;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.drcome.project.mem.service.UserDetailVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {

		response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
		
		// 세션에 필요한 값 담기
		UserDetailVO userDetailVO = (UserDetailVO) auth.getPrincipal();
		
		HttpSession session = request.getSession();
		session.setAttribute("userId", userDetailVO.getUsername()); // 아이디
		session.setAttribute("userGrade", userDetailVO.getGrade()); // 권한
		session.setAttribute("userName", userDetailVO.getUserName()); // 이름

		// 클라이언트에게 리다이렉트할 URL 설정
        String redirectUrl = "/";
        if (userDetailVO.getGrade().equals("ROLE_ADMIN")) {
            redirectUrl = "/admin";
        } else if (userDetailVO.getGrade().equals("ROLE_HOSPITAL")) {
            redirectUrl = "/hospital";
        } else if (userDetailVO.getGrade().equals("ROLE_PHARMACY")) {
            redirectUrl = "/pharmacy/status";
        }

        // 응답으로 리다이렉트할 URL 전달
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("redirectUrl", redirectUrl);
        response.getWriter().write(objectMapper.writeValueAsString(responseData));		
	}

}
