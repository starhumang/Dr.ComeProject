package com.drcome.project.mem.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.drcome.project.common.service.FileUploadService;
import com.drcome.project.common.service.PageDTO;
import com.drcome.project.common.service.PageDTO2;
import com.drcome.project.doctor.service.PatientVO;
import com.drcome.project.main.service.ClinicPayVO;
import com.drcome.project.main.service.MainService;
import com.drcome.project.main.service.PaymentVO;
import com.drcome.project.main.service.ReservationVO;
import com.drcome.project.medical.service.HospitalService;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.medical.service.NoticeAttachVO;
import com.drcome.project.medical.service.QnaVO;
import com.drcome.project.mem.mapper.UserMemberMapper;
import com.drcome.project.mem.service.AlarmVO;
import com.drcome.project.mem.service.MemVO;
import com.drcome.project.mem.service.UserMemberService;
import com.drcome.project.mem.service.UserMemberVO;
import com.drcome.project.pharmacy.PharmacySelectVO;
import com.drcome.project.pharmacy.PharmacyVO;

import lombok.extern.log4j.Log4j2;

/**
 * 개요 - 스프링 시큐리티 관련 controller 클래스
 * 
 * @author 라희재
 *
 */
@Controller
@Log4j2
public class UserMemberController {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	HospitalService hospitalService;
	
	@Autowired
	MainService mainService;

	@Autowired
	UserMemberMapper memMapper;

	@Autowired
	UserMemberService userMemService;

	@Autowired
	private FileUploadService fileUploadService;

	/**
	 * 입력 값 전화번호 형식으로 변환하는 메소드
	 * @param phoneNumber
	 * @return String: 010-0000-0000 형식으로 리턴
	 */
	private static String formatPhoneNumber(String phoneNumber) {
		// 숫자만 남기고 나머지 문자 제거
		String cleanedNumber = phoneNumber.replaceAll("[^0-9]", "");

		// 전화번호 형식으로 변환
		String formattedNumber = cleanedNumber.replaceFirst("(\\d{3})(\\d{4})(\\d+)", "$1-$2-$3");

		return formattedNumber;
	}

	/**
	 * 로그인 화면으로 이동
	 * @return String: URL
	 */
	@GetMapping("/userlogin")
	public String userLogin() {
		return "member/memlogin";
	}

	/**
	 * 계정 찾기 화면으로 이동
	 * @return String: URL
	 */
	@GetMapping("findAccount")
	public String findAccount() {
		return "member/findAccount";
	}

	/**
	 * 아이디 찾기 기능
	 * @param userName: 사용자 이름
	 * @param phone:    전화번호
	 * @return String: 찾은 아이디(없으면 "no name")
	 */
	@GetMapping("/auth/findId")
	@ResponseBody
	public String findId(@RequestParam String userName, @RequestParam String phone) {
		String userId = userMemService.findId(userName, phone);

		if (userId == null) {
			userId = "no name";
		}
		
		log.info(userId);

		return userId;
	}

	/**
	 * 비밀번호 찾기(변경) 기능 
	 * 입력 받은 비밀번호를 암호화하여 DB 업데이트
	 * @param userId:   사용자 아이디
	 * @param password: 사용자 비밀번호
	 * @return Integer: 성공 여부(1, 0) 반환
	 */
	@GetMapping("/auth/findPw")
	@ResponseBody
	public int findPw(@RequestParam String userId, @RequestParam String password) {
		MemVO vo = userMemService.getMember(userId);
		password = bCryptPasswordEncoder.encode(password);
		vo.setUserPw(password);

		int result = userMemService.updatePw(vo);
		
		log.info(result);

		return result;
	}

	/**
	 * 아이디 중복 체크 기능
	 * @param userId: 사용자 아이디
	 * @return Integer: 성공 여부(1, 0) 반환
	 */
	@GetMapping("/auth/checkId")
	@ResponseBody
	public int checkDupliId(@RequestParam String userId) {
		return userMemService.checkId(userId);
	}

	/**
	 * 전화 번호 중복 체크 기능 
	 * 입력받은 String값을 전화번호 형식으로 변환하여 중복 체크
	 * @param phoneNum: 전화번호
	 * @return Map<String, Object>: result와 msg에 결과 값을 담아서 반환
	 */
	@GetMapping("/auth/checkPhone")
	@ResponseBody
	public Map<String, Object> sendNumber(@RequestParam String phoneNum) {
		Map<String, Object> response = new HashMap<>();
		int cnt = userMemService.checkPhone(formatPhoneNumber(phoneNum));

		if (cnt == 0) {
			response = userMemService.sendNumber(phoneNum);
		} else {
			response.put("checkNum", "중복");
		}

		return response;
	}

	/**
	 * 인증 번호 전송 기능 
	 * 입력받은 전화번호로 인증 번호 전송
	 * @param phoneNum: 전화번호
	 * @return Map<String, Object>: result와 checkNum에 결과 값을 담아서 반환
	 */
	@GetMapping("/auth/checkAuthPhone")
	@ResponseBody
	public Map<String, Object> sendAuthNumber(@RequestParam String phoneNum) {
		Map<String, Object> response = new HashMap<>();
		response = userMemService.sendNumber(phoneNum);

		return response;
	}

	/**
	 * 일반 사용자 회원 가입 페이지 이동
	 * @return String: URL
	 */
	@GetMapping("/userjoin")
	public String userJoinForm() {
		return "member/userjoin";
	}

	/**
	 * 일반 사용자 회원 가입 기능 
	 * 이미지 파일 업로드 + 입력 받은 비밀번호는 암호화하고 나머지 정보와 함께 DB 인서트
	 * @param mVO: 사용자 정보 VO
	 * @return Map<String, Object>: result와 msg에 결과 값을 담아서 반환
	 */
	@PostMapping("/userjoin")
	@ResponseBody
	public Map<String, Object> userJoin(UserMemberVO mVO) {
		Map<String, Object> response = new HashMap<>();
		// 비밀번호 암호화
		String userPw = mVO.getUserPw();
		userPw = bCryptPasswordEncoder.encode(userPw);
		mVO.setUserPw(userPw);

		// 이미지 업로드
		List<String> imageList = fileUploadService.uploadFiles(mVO.getUploadFiles());
		String uploadedFileName = imageList.get(0); // 첫 번째 파일의 경로
		mVO.setIdentification(uploadedFileName);

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

	/**
	 * 의료기관 사용자 회원 가입 페이지로 이동
	 * @return String: URL
	 */
	@GetMapping("/medicaljoin")
	public String medicalJoinForm() {
		return "member/medicaljoin";
	}

	/**
	 * 의료기관 사용자 회원 가입 기능 
	 * 회원 가입 폼에서 받아온 isHospital 값으로 병원, 약국 판단하여 이미지 파일 업로드 + 입력 받은 비밀번호는 암호화하고 나머지 정보와 함께 DB 인서트
	 * @param isHospital: 의료기관 사용자 회원가입 폼에서 병원인지 아닌지 체크 여부
	 * @param hVO:        병원 사용자 정보 VO
	 * @param pVO:        약국 사용자 정보 VO
	 * @return Map<String, Object>: result와 msg에 결과 값을 담아서 반환
	 */
	@PostMapping("/medicaljoin")
	@ResponseBody
	public Map<String, Object> medicalJoin(@RequestParam("hospital") boolean isHospital, HospitalVO hVO,
			PharmacyVO pVO) {
		Map<String, Object> response = new HashMap<>();

		// 병원
		if (isHospital) {
			// 비밀번호 암호화
			String hospitalPw = hVO.getHospitalPw();
			hospitalPw = bCryptPasswordEncoder.encode(hospitalPw);
			hVO.setHospitalPw(hospitalPw);

			// 이미지 업로드
			List<String> imageList = fileUploadService.uploadFiles(hVO.getUploadFiles());
			String uploadedFileName = imageList.get(0);
			hVO.setHospitalImg(uploadedFileName);

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
		}
		// 약국
		else {
			// 비밀번호 암호화
			String pharmacyPw = pVO.getPharmacyPw();
			pharmacyPw = bCryptPasswordEncoder.encode(pharmacyPw);
			pVO.setPharmacyPw(pharmacyPw);

			// 이미지 업로드
			List<String> imageList = fileUploadService.uploadFiles(pVO.getUploadFiles());
			String uploadedFileName = imageList.get(0);
			pVO.setPharmacyImg(uploadedFileName);

			try {
				int cnt = userMemService.insertPamMember(pVO);
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
		}

		return response;
	}

	/**
	 * 사용자 회원 정보 변경 화면으로 이동
	 * @param id:   세션에 저장된 사용자 아이디
	 * @param model
	 * @return String: URL
	 */
	@GetMapping("/userupdate")
	public String userUpdateForm(@SessionAttribute(name = "userId", required = false) String id, Model model) {
		UserMemberVO userInfo = memMapper.selectMem(id);
		model.addAttribute("userInfo", userInfo);
		return "member/userupdate";
	}

	/**
	 * 사용자 회원 정보 변경 기능 
	 * 입력 받은 비밀번호는 암호화하고 나머지 정보와 함께 DB 업데이트
	 * @param mVO: 사용자 정보 VO
	 * @return Map<String, Object>: result와 msg에 결과 값을 담아서 반환
	 */
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

	/**
	 * 회원 탈퇴 기능
	 * @param userId:   사용자 아이디
	 * @param password: 사용자 비밀번호
	 * @param auth:     시큐리티 Authentication
	 * @return Integer: 성공 여부(1, 0) 반환
	 */
	@GetMapping("/userdelete")
	@ResponseBody
	public int deleteUser(@RequestParam String userId, @RequestParam String password, Authentication auth) {
		return userMemService.deleteUser(userId, password, auth);
	}
	
	@GetMapping("/useralarm")
	@ResponseBody
	public Map<String, Object> userAlarm(@SessionAttribute(name = "userId", required = false) String id) {
		Map<String, Object> map = new HashMap<>();
		List<AlarmVO> list = memMapper.myAlarmList(id);
		map.put("alarmList", list);
		
		return map;
	}
	
	@GetMapping("/checkedAlarm")
	@ResponseBody
	public int checkedAlarm(@RequestParam int alarmNo) {
		return memMapper.chekedAlarm(alarmNo);
	}

	/**
	 * 마이페이지 화면으로 이동
	 * @param id: 세션에 저장된 사용자 아이디
	 * @param model
	 * @return String: URL
	 */
	@GetMapping("/mypage")
	public String myPage(@SessionAttribute(name = "userId", required = false) String id, Model model) {
		// 사용자 정보
		UserMemberVO myprofile = memMapper.selectMem(id);
		model.addAttribute("profile", myprofile);
		
		// 예약 정보
//		List<ReservationVO> rInfo = memMapper.selectUserReserveInfo(id);		
//		model.addAttribute("reserveMyList", rInfo);

		return "member/usermypage";
	}
	
	@GetMapping("reserveMyList")
	@ResponseBody
	public Map<String, Object> clinicReserveUser(@SessionAttribute(name = "userId", required = false) String id, 
			@RequestParam Map<String, Object> param,
			@RequestParam(required = false, defaultValue = "1") int page) {

		param.put("userId", id);
		param.put("page", page);
		
		Map<String, Object> map = new HashMap<>();
		// 리스트 전체 개수
		int total = memMapper.UserReserveCount(param);
		
		System.out.println("asdga"+ total);
		
		PageDTO dto = new PageDTO(page, total);
		
		List<Map<String, Object>> reserveUserList = memMapper.selectUserReserveInfo1(param);
		
		map.put("rlist", reserveUserList);
		map.put("pagedto", dto);

		return map;
	}
	
	
	@GetMapping("/qnaListP")
	@ResponseBody
	public Map<String, Object> qnaList(@SessionAttribute(name = "userId", required = false) String id,
			@RequestParam Map<String, Object> param,
			@RequestParam(required = false, defaultValue = "1") int page) {
		
		String hid = "krrlo";
		
		param.put("userId", id);
		param.put("page", page);
		param.put("hospitalId", hid);
		
		
		Map<String, Object> map = new HashMap<>();
		// 리스트 전체 개수
		int total = userMemService.qnaUserCount(param);

		// 페이지네이션(currentpage, total)
		PageDTO2 dto = new PageDTO2(page, total);

		List<Map<String, Object>> plist = userMemService.getUserQnaList(param);

		map.put("plist", plist);
		map.put("pagedto", dto);

	    System.out.println("agsdasgdlgsad" + plist);
		return map;
	}
	
	@GetMapping("/qnaDetail")
	public String qnaDetail(@ModelAttribute QnaVO qnaVO, @ModelAttribute NoticeAttachVO attVO, Model model) {
		// QnA 정보 가져오기
	    QnaVO qnaInfo = hospitalService.getQnaInfo(qnaVO);
	    if (qnaInfo != null) {
	        model.addAttribute("qnaInfo", qnaInfo);
	    }
	    
	    // 첨부파일 가져오기
	    List<NoticeAttachVO> qnaAtt = hospitalService.selectQnaAtt(attVO);
	    model.addAttribute("qnaAtt", qnaAtt);
	    
	    // 답변 정보가 있는 경우
	    if (qnaVO.getAnsCode() != null && !"undefined".equals(qnaVO.getAnsCode())) {
	        // 답변 정보 가져오기
	        QnaVO ansInfo = hospitalService.getAnsInfo(qnaVO);
	        if (ansInfo != null) {
	            model.addAttribute("ansInfo", ansInfo);
	        }
	    }
	    
	    String hospitalId = qnaVO.getHospitalId();
	    
	    HospitalVO hosInfo = mainService.getHos(hospitalId);
		model.addAttribute("hosInfo", hosInfo);
	    
	    // 결과를 보여줄 뷰 페이지의 이름을 반환
		return "user/qnaDetail";
	}
	
	/**
	 * 선택한 약국 정보 확인 기능
	 * @param ReserveNo: 예약 번호
	 * @param model
	 * @return 선택한 약국 정보
	 */
	@GetMapping("/mypselect")
	@ResponseBody
	public Map<String, Object> myPselect(@RequestParam String ReserveNo, Model model) {
		Map<String, Object> map = new HashMap<>();
		int reserveNo = Integer.parseInt(ReserveNo);
		
		List<PharmacySelectVO> myplist = memMapper.myPharmacySelect(reserveNo);
		
		if (myplist != null) {
			map.put("result", 1);
			map.put("myplist", myplist);
		} else {
			map.put("result", 0);
		}
		
		
		return map;
	}

	/**
	 * 비대면 진료 화면으로 이동
	 * @param reserveNo: 예약 번호
	 * @param vo: 환자 정보 VO
	 * @param model
	 * @return String: URL
	 */
	@GetMapping("untactPatient/{reserveNo}")
	public String untactPatient(@PathVariable("reserveNo") String reserveNo, PatientVO vo, Model model) {
		model.addAttribute("rNo", reserveNo);
		return "doctor/untactPatient";
	}

	/**
	 * 결제 페이지로 이동
	 * @param ReserveNo: 예약 번호
	 * @param id: 세션에 저장된 사용자 아이디
	 * @param model
	 * @return String: URL
	 */
	@GetMapping("/payment/{ReserveNo}")
	public String payment(@PathVariable("ReserveNo") String ReserveNo, @SessionAttribute(name = "userId", required = false) String id, Model model) {
		// 예약 번호
		int reserveNo = Integer.parseInt(ReserveNo);
		model.addAttribute("rNo", reserveNo);
		
		// 예약 정보
		ClinicPayVO cInfo = memMapper.selectClinicPay(reserveNo);
		model.addAttribute("cInfo", cInfo);
		
		// 사용자 정보
		UserMemberVO myInfo = memMapper.selectMem(id);
		model.addAttribute("myInfo", myInfo);
		
		return "user/clinicPayment";
	}
	
	/**
	 * 결제 기능
	 * @param vo: 결제 정보 VO
	 * @return Map<String, Object>: result와 msg에 결과 값을 담아서 반환
	 */
	@PostMapping("/payment/updateReserve")
	@ResponseBody
	public Map<String, Object> updateReserve(PaymentVO vo){		
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			int cnt = userMemService.paymentUpdate(vo);
			if (cnt > 0) {
				response.put("result", true);
				response.put("msg", "결제가 완료되었습니다.");
			} else {
				response.put("result", false);
				response.put("msg", "오류가 발생했습니다. 관리자에게 문의하세요.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("result", false);
			response.put("msg", "오류가 발생했습니다. 관리자에게 문의하세요.");
		}
		return response;
	}
	
}