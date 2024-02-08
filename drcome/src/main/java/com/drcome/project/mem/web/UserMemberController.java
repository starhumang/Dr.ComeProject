package com.drcome.project.mem.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.drcome.project.FileUploadService;
import com.drcome.project.medical.service.HospitalService;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.mem.mapper.UserMemberMapper;
import com.drcome.project.mem.service.MemVO;
import com.drcome.project.mem.service.UserMemberService;
import com.drcome.project.mem.service.UserMemberVO;
import com.drcome.project.pharmacy.PharmacyVO;

@Controller
public class UserMemberController {

	@Autowired
	UserMemberMapper memMapper;

	@Autowired
	UserMemberService userMemService;

	@Autowired
	HospitalService hospitalService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private FileUploadService fileUploadService;

	private static String formatPhoneNumber(String phoneNumber) {
		// 숫자만 남기고 나머지 문자 제거
		String cleanedNumber = phoneNumber.replaceAll("[^0-9]", "");

		// 전화번호 형식으로 변환
		String formattedNumber = cleanedNumber.replaceFirst("(\\d{3})(\\d{4})(\\d+)", "$1-$2-$3");

		return formattedNumber;
	}

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

	@GetMapping("/userupdate")
	public String userUpdateForm(@SessionAttribute(name = "userId", required = false) String id, Model model) {
		UserMemberVO userInfo = memMapper.selectMem(id);
		model.addAttribute("userInfo", userInfo); 
		return "/member/userupdate";
	}

	@PostMapping("/userupdate")
	@ResponseBody
	public Map<String, Object> userUpdate(UserMemberVO mVO) {
		Map<String, Object> response = new HashMap<>();
		String password = mVO.getUserPw();
		password = bCryptPasswordEncoder.encode(password);
		mVO.setUserPw(password);

		try {
			int cnt = userMemService.updateUserInfo(mVO);
			if (cnt > 0) {
				response.put("result", true);
				response.put("msg", "정상적으로 수정되었습니다.");
			} else {
				response.put("result", false);
				response.put("msg", "수정에 실패했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("result", false);
			response.put("msg", "에러 발생");
		}
		return response;
	}

	@GetMapping("/userjoin")
	public String userJoinForm() {
		return "/member/userjoin";
	}

	@PostMapping("/userjoin")
	@ResponseBody
	public Map<String, Object> userJoin(UserMemberVO mVO, HttpServletResponse resp) {
		Map<String, Object> response = new HashMap<>();
		String userPw = mVO.getUserPw();
		userPw = bCryptPasswordEncoder.encode(userPw);
		mVO.setUserPw(userPw);

		System.out.println(mVO);

		List<String> imageList = fileUploadService.uploadFiles(mVO.getUploadFiles());

		String uploadedFileName = imageList.get(0); // 첫 번째 파일의 경로
		mVO.setIdentification(uploadedFileName);

		System.out.println(mVO);

		try {
			int cnt = userMemService.insertUserMember(mVO);
			if (cnt > 0) {
				response.put("result", true);
				response.put("msg", "회원가입이 성공적으로 완료되었습니다.");
			} else {
				response.put("result", false);
				response.put("msg", "회원가입에 실패했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("result", false);
			response.put("msg", "에러 발생");
		}

		return response;
	}

	@GetMapping("/medicaljoin")
	public String medicalJoinForm() {
		return "/member/medicaljoin";
	}

	@PostMapping("/medicaljoin")
	@ResponseBody
	public Map<String, Object> medicalJoin(HospitalVO hVO, PharmacyVO pVO) {
		Map<String, Object> response = new HashMap<>();
		String hospitalPw = hVO.getHospitalPw();
		hospitalPw = bCryptPasswordEncoder.encode(hospitalPw);
		hVO.setHospitalPw(hospitalPw);

		List<String> imageList = fileUploadService.uploadFiles(hVO.getUploadFiles());

		String uploadedFileName = imageList.get(0); // 첫 번째 파일의 경로
		hVO.setHospitalImg(uploadedFileName);

		System.out.println(hVO);

		try {
			int cnt = userMemService.insertHosMember(hVO);
			if (cnt > 0) {
				response.put("result", true);
				response.put("msg", "회원가입이 성공적으로 완료되었습니다.");
			} else {
				response.put("result", false);
				response.put("msg", "회원가입에 실패했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("result", false);
			response.put("msg", "에러 발생");
		}

		return response;
	}

	@GetMapping("/mypage")
	public String myPage(@SessionAttribute(name = "userId", required = false) String id, Model model) {
		UserMemberVO myprofile = memMapper.selectMem(id);
		model.addAttribute("profile", myprofile);
		
		String hospitalId = "krrlo";
		int doctorNo = 123;
		
		List<Map<String, Object>> reserveMyList = hospitalService.getReserveDrList(hospitalId, doctorNo);
		model.addAttribute("reserveMyList", reserveMyList);

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
		Map<String, Object> response = new HashMap<>();
		System.out.println(phoneNum);
		System.out.println(formatPhoneNumber(phoneNum));
		int cnt = userMemService.checkPhone(formatPhoneNumber(phoneNum));
		System.out.println(cnt);
		if (cnt == 0) {
			response = userMemService.sendNumber(phoneNum);
		} else {
			response.put("checkNum", "중복");
		}
		System.out.println(response);
		
		return response;
	}
	
	@GetMapping("/auth/checkAuthPhone")
	@ResponseBody
	public Map<String, Object> sendAuthNumber(@RequestParam String phoneNum) {
		Map<String, Object> response = new HashMap<>();
		response = userMemService.sendNumber(phoneNum);

		return response;
	}

}
