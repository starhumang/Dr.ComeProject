package com.drcome.project.common.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.drcome.project.FileUploadService;
import com.drcome.project.common.mapper.UserMemberMapper;
import com.drcome.project.common.service.UserMemberService;
import com.drcome.project.common.service.UserMemberVO;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

@Controller
public class UserMemberController {

	@Autowired
	UserMemberMapper memMapper;

	@Autowired
	UserMemberService userMemService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    private FileUploadService fileUploadService;

	@GetMapping(value = { "/admin/", "/admin/home"})
	public String adminHome() {
		return "/admin/home";
	}
	
	@GetMapping("/userlogin")
	public String userLogin() {
		return "/member/memlogin";
	}

	@GetMapping("/userjoin")
	public String userJoinForm() {
		return "/member/userjoin";
	}

	@PostMapping("/userjoin")
	public void userJoin(UserMemberVO mVO, HttpServletResponse resp) {
		String userPw = mVO.getUserPw();
		userPw = bCryptPasswordEncoder.encode(userPw);
		mVO.setUserPw(userPw);
		
		System.out.println(mVO);
		
		resp.setContentType("text/html; charset=UTF-8");
		
		List<String> imageList = fileUploadService.uploadFiles(mVO.getUploadFiles());
		
		String uploadedFileName = imageList.get(0); // 첫 번째 파일의 경로
        mVO.setIdentification(uploadedFileName);

        System.out.println(mVO);
        
		int cnt = userMemService.insertUserMember(mVO);
		try {
			if (cnt > 0) {
				PrintWriter out = resp.getWriter();
				out.println("<script language='javascript'>");
				out.println("alert('[회원가입성공] " + mVO.getUserName() + "님 환영합니다'); location.href='/';");
				out.println("</script>");
				out.flush();
			} else {
				PrintWriter out = resp.getWriter();
				out.println("<script language='javascript'>");
				out.println("alert('[회원가입실패] 다시 시도해주세요 :('); location.href='/';");
				out.println("</script>");
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace(); // 예외 처리 추가
		}
	}
	
	@GetMapping("/medicaljoin")
	public String medicalJoinForm() {
		return "/member/medicaljoin";
	}
	
	@PostMapping("/medicaljoin")
	public void medicalJoin(HospitalVO hVO, PharmacyVO pVO, HttpServletResponse resp) {
		String hospitalPw = hVO.getHospitalPw();
		hospitalPw = bCryptPasswordEncoder.encode(hospitalPw);
		hVO.setHospitalPw(hospitalPw);
		
		resp.setContentType("text/html; charset=UTF-8");

		int cnt = userMemService.insertHosMember(hVO);
		try {
			if (cnt > 0) {
				PrintWriter out = resp.getWriter();
				out.println("<script language='javascript'>");
				out.println("alert('[회원가입성공] " + hVO.getHospitalName() + "병원님 환영합니다'); location.href='/hospital';");
				out.println("</script>");
				out.flush();
			} else {
				PrintWriter out = resp.getWriter();
				out.println("<script language='javascript'>");
				out.println("alert('[회원가입실패] 다시 시도해주세요 :('); location.href='/userlogin';");
				out.println("</script>");
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace(); // 예외 처리 추가
		}
	}
	
	@GetMapping("/mypage")
	public String myPage(@SessionAttribute(name = "userId", required = false) String id, Model model) {
		UserMemberVO myprofile = memMapper.selectMem(id);
		model.addAttribute("profile", myprofile);
		return "/member/userpage";
	}
	
	@GetMapping("/checkId")
	@ResponseBody
	public String checkDupliId(@RequestParam String userId) {
		int cnt = memMapper.checkId(userId);
		String msg = null;
		if(cnt == 1) {
			msg = "중복";
		} else {
			msg = "아님";
		}
		System.out.println(msg);

		return msg;
	}

}
