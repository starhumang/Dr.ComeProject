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

import com.drcome.project.common.service.UserDetailVO;
import com.drcome.project.common.service.UserMemberService;
import com.drcome.project.common.service.UserMemberVO;

@Service
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {

		response.setContentType("text/html; charset=UTF-8");
		
		// 세션에 필요한 값 담기
		UserDetailVO userDetailVO = (UserDetailVO) auth.getPrincipal();

		HttpSession session = request.getSession();
		session.setAttribute("userId", userDetailVO.getUserId()); // 아이디
		session.setAttribute("userGrade", userDetailVO.getGrade()); // 권한
		session.setAttribute("userName", userDetailVO.getUserName()); // 이름
		
		// 세션 정보를 세션 스토리지에 저장
//        String script = "sessionStorage.setItem('userId', '" + userDetailVO.getUserId() + "');";
//        script += "sessionStorage.setItem('userGrade', '" + userDetailVO.getGrade() + "');";
//        script += "sessionStorage.setItem('userName', '" + userDetailVO.getUserName() + "');";
//        response.getWriter().write("<script>" + script + "</script>");

//		System.out.println("userId: " + session.getAttribute("userId"));
//		System.out.println("userGrade: " + session.getAttribute("userGrade"));
//		System.out.println("userName: " + session.getAttribute("userName"));

		// location할 페이지 설정
		String page = null;
		if (userDetailVO.getGrade().equals("ROLE_ADMIN")) {
			page = "/admin/home";
		} else {
			page = "/";
		}

		// alert + location
		try {
			PrintWriter out = response.getWriter();
			out.println("<script language='javascript'>");
			out.println("alert('" + userDetailVO.getUserName() + "님 반갑습니다.'); location.href='" + page + "';");
			out.println("</script>");

			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
