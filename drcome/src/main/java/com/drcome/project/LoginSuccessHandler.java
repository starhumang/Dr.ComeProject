package com.drcome.project;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.drcome.project.mem.service.UserDetailVO;
import com.drcome.project.mem.service.UserMemberService;
import com.drcome.project.mem.service.UserMemberVO;

@Service
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {

		response.setContentType("text/html; charset=UTF-8");
		
		// 세션에 필요한 값 담기
		UserDetailVO userDetailVO = (UserDetailVO) auth.getPrincipal();

		HttpSession session = request.getSession();
		session.setAttribute("userId", userDetailVO.getUsername()); // 아이디
		session.setAttribute("userGrade", userDetailVO.getGrade()); // 권한
		session.setAttribute("userName", userDetailVO.getUserName()); // 이름

		// location할 페이지 설정
		String page = null;
		if (userDetailVO.getGrade().equals("ROLE_ADMIN")) {
			page = "/admin";
			response.sendRedirect(page);
		} else if (userDetailVO.getGrade().equals("ROLE_HOSPITAL")) {
			page = "/hospital";
			response.sendRedirect(page);
		} else if (userDetailVO.getGrade().equals("ROLE_PHARMACY")) {
			page = "/pharmacy";
			response.sendRedirect(page);
		} else {
			page = "/";
			response.sendRedirect(page);
		}
		
	}

}
