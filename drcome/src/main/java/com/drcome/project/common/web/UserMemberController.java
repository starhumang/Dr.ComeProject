package com.drcome.project.common.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.drcome.project.common.service.MemVO;
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
	
	@GetMapping("/userlogin")
	public String userLogin() {
		return "/member/memlogin";
	}
	
	@GetMapping("findAccount")
	public String findAccount() {
		return "/member/findAccount";
	}
	
	@GetMapping("/auth/findId")
	@ResponseBody
	public String findId(@RequestParam String userName, @RequestParam String phone) {
	    System.out.println("이름: " + userName + "전화번호: " + phone);

	    String userId = userMemService.findId(userName, phone);
	    
	    System.out.println("값: " + userId);
	    
	    if (userId == null) {
	    	userId = "no name";
	    }
	    
	    return userId;
	}
	
	@GetMapping("/auth/findPw")
	@ResponseBody
	public int findPw(@RequestParam String userId, @RequestParam String password) {
		MemVO vo = userMemService.getMember(userId);
		password = bCryptPasswordEncoder.encode(password);
		vo.setUserPw(password);
		
		int result = userMemService.updatePw(vo);
				
		return result;
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
		
		List<String> imageList = fileUploadService.uploadFiles(hVO.getUploadFiles());
		
		String uploadedFileName = imageList.get(0); // 첫 번째 파일의 경로
		hVO.setHospitalImg(uploadedFileName);

        System.out.println(hVO);

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
	
	@GetMapping("/auth/checkId")
	@ResponseBody
	public int checkDupliId(@RequestParam String userId) {
	    System.out.println("중복체크할 아이디: " + userId);

	    int cnt = userMemService.checkId(userId);
	    
	    System.out.println("값: " + cnt);
	    
	    return cnt;
	}
	
	@GetMapping("/auth/checkPhone")
	@ResponseBody
	public Map<String, Object> sendNumber(@RequestParam String phoneNum) {
		return userMemService.sendNumber(phoneNum);
	}

}
