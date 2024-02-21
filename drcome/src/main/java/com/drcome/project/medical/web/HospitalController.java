package com.drcome.project.medical.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.common.service.FileUploadService;
import com.drcome.project.common.service.PageDTO2;
import com.drcome.project.medical.service.DoctorTimeVO;
import com.drcome.project.medical.service.DoctorVO;
import com.drcome.project.medical.service.HospitalService;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.medical.service.NoticeAttachVO;
import com.drcome.project.medical.service.NoticeVO;
import com.drcome.project.medical.service.QnaVO;
import com.drcome.project.mem.mapper.UserMemberMapper;
import com.drcome.project.mem.service.UserMemberService;
/**
 * 병원 회원 관리 페이지
 * @author 최다예
 *
 */
@Controller
public class HospitalController {
	@Autowired
	// HospitalRepository repo;
	HospitalService hospitalService;

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	UserMemberMapper memMapper;

	@Autowired
	UserMemberService userMemService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * 공통 정보 : 병원 따로 빼기
	 * @param principal
	 * @return
	 */
	@ModelAttribute("hospitalSel")
	public Hospital getServerTime(Principal principal) {
		String hospitalId = principal.getName();
		Hospital hosSel = hospitalService.findByhospitalId(hospitalId);
		return hosSel;
	}
	
	/**
	 * 대시보드 - today 진료 내역 및 예약 내역 리스트
	 * 		   QnA 리스트(답변O/답변X)
	 * @param principal
	 * @param hospitalId
	 * @param model
	 * @return
	 */
	@GetMapping(value = { "/hospital", "/hospitalHome" })
	public String home(Principal principal, String hospitalId, Model model) {
		hospitalId = principal.getName();
		List<Map<String, Object>> tolist = hospitalService.getTodayReserve(hospitalId);
		List<Map<String, Object>> QnAO = hospitalService.getQnAO(hospitalId);
		List<Map<String, Object>> QnAX = hospitalService.getQnAX(hospitalId);
		
		int resCnt = hospitalService.selectReserveCnt(hospitalId);
		int qnaCnt = hospitalService.selectQnaCnt(hospitalId);
		int payMonth = hospitalService.selectPayMonth(hospitalId);
		int c2Rate = hospitalService.selectC2Rate(hospitalId);
		
		model.addAttribute("tolist", tolist);
		model.addAttribute("QnAO", QnAO);
		model.addAttribute("QnAX", QnAX);
		model.addAttribute("resCnt", resCnt);
		model.addAttribute("qnaCnt", qnaCnt);
		model.addAttribute("payMonth", payMonth);
		model.addAttribute("c2Rate", c2Rate);
		return "hospital/home";
	}

	/**
	 * 병원프로필 : 병원 조회, 병원-의사 조회
	 * @param principal
	 * @param hospitalId
	 * @param model
	 * @return
	 */
	@GetMapping("/hospital/myProfile")
	public String findHospital(Principal principal, Model model) {
		String hospitalId = principal.getName();
		List<DoctorVO> docList = hospitalService.getDoctorAll(hospitalId);
		model.addAttribute("docList", docList);
		return "hospital/myProfile";
	}

	/**
	 * 환자리스트 - 병원 환자 전체 조회
	 * @param principal
	 * @param hospitalId
	 * @param model
	 * @return
	 */
	@GetMapping("/hospital/patientList")
	public String searchPatient() {
		return "hospital/patientList";
	}
	
	/**
	 * 환자 페이지 - 페이징&검색
	 * @param principal
	 * @param param : type, keyword
	 * @param page
	 * @return
	 */
	@GetMapping("/hospital/patientListP")
	@ResponseBody
	public Map<String, Object> patientList( Principal principal, 
											@RequestParam Map<String, Object> param,
											@RequestParam(required = false, defaultValue = "1") int page) {
		
		String hospitalId = principal.getName();
		param.put("hospitalId", hospitalId);
		param.put("page", page);
		
		Map<String, Object> map = new HashMap<>();
		
		// 리스트 전체 개수
		int total = hospitalService.patientCount(param);

		// 페이지네이션(currentpage, total)
		PageDTO2 dto = new PageDTO2(page, total);

		List<Map<String, Object>> plist = hospitalService.getPaientList(param);
			
		// ajax는 return으로...

		map.put("plist", plist);
		map.put("pagedto", dto);

		return map;
	}

	/**
	 * 환자 페이지 - 해당 환자 진단 기록 리스트
	 * @param principal  
	 * @param hospitalId
	 * @param patientNo
	 * @param model
	 * @return hospital/patientDetail
	 */
	@GetMapping("/hospital/patientList/patientDetail")
	public String detailPatient(Principal principal, String hospitalId, Integer patientNo, Model model) {
		hospitalId = principal.getName();
		model.addAttribute("patientNo", patientNo);
		return "hospital/patientDetail";
	}
	
	/**
	 * 환자 페이지 - 해당 환자 진단 기록 리스트(Ajax)&검색,페이징
	 * @param principal
	 * @param param :keyword, type, patientNo
	 * @param page
	 * @return
	 */
	@GetMapping("/hospital/patientList/patientDetailP")
	@ResponseBody
	public Map<String, Object> patientInfoList(Principal principal, 
			@RequestParam Map<String, Object> param,
			@RequestParam(required = false, defaultValue = "1") int page) {
		
		String hospitalId = principal.getName();
		param.put("hospitalId", hospitalId);
		param.put("page", page);
		
		Map<String, Object> map = new HashMap();
		// 리스트 전체 개수
		int total = hospitalService.patientInfoCount(param);

		// 페이지네이션(currentpage, total)
		PageDTO2 dto = new PageDTO2(page, total);

		List<Map<String, Object>> plist = hospitalService.getPaientDetailList(param);

		// ajax는 return으로...

		map.put("plist", plist);
		map.put("pagedto", dto);

		return map;
	}
	
	/**
	 * 환자 페이지 - 해당 환자 진단 기록 단건 검색
	 * @param principal
	 * @param hospitalId
	 * @param patientNo
	 * @param clinicNo
	 * @param model
	 * @return
	 */
	@GetMapping("/hospital/patientList/patientDetail/patientClinicInfo")
	public String patientClinic(Principal principal, String hospitalId, Integer patientNo, Integer clinicNo, Model model) {
		hospitalId = principal.getName();
		
		Map<String, Object> patientInfo = hospitalService.getPaientClinicInfo(hospitalId, patientNo, clinicNo);
		List<Map<String, Object>> patientPill = hospitalService.getpaientPillInfo(clinicNo);
		
		model.addAttribute("patientNo", patientNo);
		model.addAttribute("clinicNo", clinicNo);
		model.addAttribute("patientInfo", patientInfo);
		model.addAttribute("patientPill", patientPill);
		return "hospital/patientClinicInfo";
	}
	
	/**
	 * 진료 및 예약 - 전체보기(Main)
	 * @param principal
	 * @param hospitalId
	 * @param date
	 * @param reserveKindstatus
	 * @return
	 */
	@GetMapping("/hospital/clinicMain")
	public String clinicReserve(Principal principal, String hospitalId, String date, String reserveKindstatus) {
		hospitalId = principal.getName();
		return "hospital/clinicMain";
	}
	/**
	 * 진료 및 예약 - 전체보기(Main)Ajax
	 * @param principal
	 * @param hospitalId
	 * @param date
	 * @param reserveKindstatus
	 * @return
	 */
	@GetMapping("/hospital/clinicMain/ajax")
	@ResponseBody
	public List<Map<String, Object>> clinicReserve1(Principal principal, String hospitalId, String date, String reserveKindstatus) {
		hospitalId = principal.getName();
		List<Map<String, Object>> reserveList = hospitalService.getRerveList(hospitalId, date, reserveKindstatus);
		return reserveList;
	}
	
	/**
	 * 진료 및 예약 - 의사별
	 * @param principal
	 * @param hospitalId
	 * @param doctorNo
	 * @param date
	 * @param reserveKindstatus
	 * @return
	 */
	@GetMapping("/hospital/clinicDr")
	public String clinicReserveDr(Principal principal, String hospitalId, Integer doctorNo, String date, String reserveKindstatus) {
		hospitalId = principal.getName();
		return "hospital/clinicDr";
	}
	
	/**
	 * 진료 및 예약 - 의사별Ajax & 의사선택, 날짜선택, 상태선택
	 * @param principal
	 * @param hospitalId
	 * @param doctorNo
	 * @param date
	 * @param reserveKindstatus
	 * @param model
	 * @return
	 */
	@GetMapping("/hospital/clinicDr/ajax")
	@ResponseBody
	public List<Map<String, Object>> clinicReserveDr1(Principal principal, String hospitalId, Integer doctorNo, String date, String reserveKindstatus, Model model) {
		hospitalId = principal.getName();
		List<Map<String, Object>> reserveDrList = hospitalService.getReserveDrList(hospitalId, doctorNo, date, reserveKindstatus);
		return reserveDrList;
	}
	
	/**
	 * 진료 및 예약 - 의사별Ajax & 의사리스트
	 * @param principal
	 * @param hospitalId
	 * @return
	 */
	@GetMapping("/hospital/clinicDr/allDr")
	@ResponseBody
	public List<Map<String, Object>> allDrList(Principal principal, String hospitalId) {
		hospitalId = principal.getName();
		List<Map<String, Object>> DrAllList = hospitalService.getDrAllList(hospitalId);
		return DrAllList;
	}
	
	/**
	 * 환자 선택 약국 가져오기 페이지
	 * @param principal
	 * @param param : reserveNo, clinicNo
	 * @param model
	 * @return
	 */
	@GetMapping("/hospital/selPharmacyList")
	public String selPharmacyList(Principal principal,
								  @RequestParam Map<String, Object> param,
								  Model model) {
		
		String hospitalId = principal.getName();
		param.put("hospitalId", hospitalId);
		
		List<Map<String, Object>> pharList = hospitalService.selectPharList(param);
		
		int clinicNo = 0;
		//pharList 중에 clinicNo 값을 우선 Object에 들어있기에 String으로 바꾼 후,
		if(pharList.size() > 0) {
			Object clinicNoObject = pharList.get(0).get("clinicNo");
			String test2 = String.valueOf(clinicNoObject);
			
			//이걸 다시 int로 형변환
			clinicNo =Integer.parseInt(test2);
		} 
	    
	    //첫 번째 clinicNo를 사용하여 getpaientPillInfo 메서드 호출
	    List<Map<String, Object>> perscrip = hospitalService.getpaientPillInfo(clinicNo);
		
		model.addAttribute("pharList", pharList);
		model.addAttribute("perscrip", perscrip);
		
		return "hospital/selPharmacyList";
	}
	
	/**
	 * 약국 처방전 발송
	 * @param pharmacySelectNos
	 * @return
	 */
	@PostMapping("/hospital/updatePharmacySelect")
	@ResponseBody
	public String updatePharmacySelect(@RequestBody List<Map<String, Long>> pharmacySelectNos) {
	    // pharmacySelectNos를 사용하여 업데이트 작업 수행
	
	    hospitalService.updateSendPersStatus(pharmacySelectNos);
	
	    return "hospital/selPharmacyList";
	}
	/**
	 * 처방전 발송 후 상태변경 -> 처방전전송 버튼 막기
	 * @param parameter
	 */
    @PostMapping("/hospital/updateReservationStatus")
    @ResponseBody
    public void updateReservationStatus(@RequestBody Map<String, Object> parameter) {
        hospitalService.updateReservationStatus(parameter);
    }

	/**
	 * QnA 전체 리스트
	 * @param principal
	 * @param hospitalId
	 * @param model
	 * @return
	 */
	@GetMapping("/hospital/qnaList")
	public String qnaList(Principal principal, String hospitalId, Model model) {
		hospitalId = principal.getName();
		return "hospital/qnaList";
	}
	
	/**
	 * QnA 전체 - 페이징&검색
	 * @param principal
	 * @param param : page, type, ansStatus, keyword, categoryStatus
	 * @param page
	 * @return
	 */
	@GetMapping("/hospital/qnaListP")
	@ResponseBody
	public Map<String, Object> qnaList(Principal principal, 
			@RequestParam Map<String, Object> param,
			@RequestParam(required = false, defaultValue = "1") int page) {
		
		String hospitalId = principal.getName();
		param.put("hospitalId", hospitalId);
		param.put("page", page);
		
		Map<String, Object> map = new HashMap();
		// 리스트 전체 개수
		int total = hospitalService.qnaCount(param);

		// 페이지네이션(currentpage, total)
		PageDTO2 dto = new PageDTO2(page, total);

		List<Map<String, Object>> plist = hospitalService.getQnaList(param);

		// ajax는 return으로...

		map.put("plist", plist);
		map.put("pagedto", dto);

		return map;
	}
	
	/**
	 * QnA 단건상세 + 첨부파일 가져오기(1:1)
	 * @param principal
	 * @param hospitalId
	 * @param qnaVO
	 * @param attVO
	 * @param model
	 * @return
	 */
	@GetMapping("/hospital/qnaList/qnaDetail")
	public String qnaInfo(Principal principal,
						  String hospitalId,
						  @ModelAttribute QnaVO qnaVO,
						  @ModelAttribute NoticeAttachVO attVO,
						  Model model) {
		
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
	        model.addAttribute("ansInfo", ansInfo);
	    }
	    // 결과를 보여줄 뷰 페이지의 이름을 반환
	    return "hospital/qnaDetail";
	}

	/**
	 * QnA 답변 등록 -FORM
	 * @param qnaNo
	 * @param model
	 * @return
	 */
	@GetMapping("/hospital/qnaAnsForm")
	public String insertQnaAnsForm(@RequestParam String qnaNo, Model model) {
		model.addAttribute("qnaNo", qnaNo);
	    return "hospital/qnaAnsForm";
	}

	/**
	 * QnA 답변 등록 - PROCESS
	 * @param principal
	 * @param vo
	 * @param resp
	 * @param request
	 */
	@PostMapping("/hospital/qnaAnsForm")
	public void insertQnaAnsProcess(Principal principal,
									@ModelAttribute QnaVO vo,
									HttpServletResponse resp,
									HttpServletRequest request) {

		resp.setContentType("text/html; charset=UTF-8");

		String hospitalId = principal.getName();
		vo.setHospitalId(hospitalId);
		
		//realQnaNo 차자오기
		int realQnaNo = vo.getQnaNo();
		
		hospitalService.insertQnaAns(vo);
		
		if (! vo.getUploadFiles()[0].isEmpty()) {
			List<String> fileNames = fileUploadService.uploadFiles(vo.getUploadFiles());

			// 파일정보
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("chkList", fileNames);

			for (String name : fileNames) {
				vo.setFileName(name);
				hospitalService.insertAttachQnaAns(vo);
			}
		}
		
		vo.setAnsCode(vo.getQnaNo());		
		vo.setQnaNo(realQnaNo);
		hospitalService.updateQnaStatus(vo);
		
		PrintWriter out;
		try {
			out = resp.getWriter();
			out.println("<script language='javascript'>");
			out.println("alert('등록을 성공했습니다.'); location.href='/hospital/qnaList';");
			out.println("</script>");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * qna User - FORM
	 * @return
	 */
//	@GetMapping("/qnaUserForm")
//	public String insertQnaMemForm() {
//	    return "user/qnaUserForm";
//	}
	
	/**
	 * qna User - PROCESS
	 * @param principal
	 * @param vo
	 * @param resp
	 * @param request
	 */
//	@PostMapping("/hospital/qnaUserForm")
//	public void insertQnaMemProcess(Principal principal,
//									@ModelAttribute QnaVO vo,
//									HttpServletResponse resp,
//									HttpServletRequest request) {
//
//		resp.setContentType("text/html; charset=UTF-8");
//
//		String hospitalId = principal.getName();
//		String userId ="user1";
//		vo.setHospitalId(hospitalId);
//		vo.setUserId(userId);
//		
//		if (vo.getUploadFiles()[0].isEmpty()) {
//			hospitalService.insertQnaMem(vo);
//			
//		}else {	
//			
//			hospitalService.insertQnaMem(vo);
//			
//			List<String> fileNames = fileUploadService.uploadFiles(vo.getUploadFiles());
//
//			// 파일정보
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("chkList", fileNames);
//
//			for (String name : fileNames) {
//				vo.setFileName(name);
//				hospitalService.insertAttachQnaAns(vo);
//			}
//			
//		}
//		PrintWriter out;
//		try {
//			out = resp.getWriter();
//			out.println("<script language='javascript'>");
//			out.println("alert('등록을 성공했습니다.'); location.href='/hospital/qnaList';");
//			out.println("</script>");
//			out.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
		
	/**
	 * 공지사항 페이지
	 * @param principal
	 * @param hospitalId
	 * @return
	 */
	@GetMapping("/hospital/noticeList")
	public String noticeList(Principal principal, String hospitalId) {
		hospitalId = principal.getName();

		return "hospital/noticeList";
	}
	
	/**
	 * 공지사항 페이지 - 페이징,검색
	 * @param principal
	 * @param hospitalId
	 * @param page
	 * @param type
	 * @param keyword
	 * @return
	 */
	@GetMapping("/hospital/noticeListP")
	@ResponseBody
	public Map<String, Object> noticeList(Principal principal, String hospitalId,
			@RequestParam(required = false, defaultValue = "1") String page,
			@RequestParam(required = false, defaultValue = "0") int type,
            @RequestParam("keyword") String keyword) {
		hospitalId = principal.getName();

		Map<String, Object> map = new HashMap();
		// 리스트 전체 개수
		int total = hospitalService.noticeCount(type, keyword, hospitalId);

		// 선택 페이지 변환
		int cpage = Integer.parseInt(page);

		// 페이지네이션(currentpage, total)
		PageDTO2 dto = new PageDTO2(cpage, total);

		List<Map<String, Object>> plist = hospitalService.getNoticeList(cpage, type, keyword, hospitalId);

		// ajax는 return으로...

		map.put("plist", plist);
		map.put("pagedto", dto);

		return map;
	}
	
	/**
	 * 공지사항 페이지 - 단건상세조회
	 * @param principal
	 * @param noticeVO
	 * @param model
	 * @return
	 */
	@GetMapping("/hospital/noticeList/noticeDetail")
	public String noticeDetail(Principal principal, NoticeVO noticeVO, Model model) {
		noticeVO.setHospitalId(principal.getName());
		int noticeNo = noticeVO.getNoticeNo();
		
		NoticeVO notice = hospitalService.getNoticeDetail(noticeVO);

		model.addAttribute("noticeNo", noticeNo);
		model.addAttribute("noticeList", notice);
		return "hospital/noticeDetail";
	}
	
	/**
	 * 공지사항 페이지 - 공지사항 등록 FORM
	 * @return
	 */
	@GetMapping("/hospital/noticeForm")
	public String insertNoticeForm() {
		return "hospital/noticeForm";
	}
	
	/**
	 * 공지사항 페이지 - 공지사항 등록 PROCESS
	 * @param principal
	 * @param vo
	 * @param resp
	 * @param request
	 */
	@PostMapping("/hospital/noticeForm")
	public void insertNoticeInfoProcess(Principal principal, NoticeVO vo, HttpServletResponse resp,
			HttpServletRequest request) {

		resp.setContentType("text/html; charset=UTF-8");

		String hospitalId = principal.getName();
		vo.setHospitalId(hospitalId);

		// notice정보..
		hospitalService.insertNoticeInfo(vo);
		
		//첨부파일 등록
		if (! vo.getUploadFiles()[0].isEmpty()) {
			List<String> fileNames = fileUploadService.uploadFiles(vo.getUploadFiles());

			// 파일정보...
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("chkList", fileNames);

			for (String name : fileNames) {
				vo.setFileName(name);
				hospitalService.insertAttach(vo);
			}
		}
		
		PrintWriter out;
		try {
			out = resp.getWriter();
			out.println("<script language='javascript'>");
			out.println("alert('등록을 성공했습니다.'); location.href='/hospital/noticeList';");
			out.println("</script>");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 공지사항 수정 - FORM
	 * @param principal
	 * @param noticeNo
	 * @param noticeVO
	 * @param model
	 * @return
	 */
	@GetMapping("/hospital/noticeUpdate/{noticeNo}")
	public String noticeUpdateForm(Principal principal, @PathVariable("noticeNo") int noticeNo, NoticeVO noticeVO, Model model) {
		noticeVO.setHospitalId(principal.getName());
		NoticeVO noticeList = hospitalService.getNoticeDetail(noticeVO);
		model.addAttribute("noticeNo", noticeNo);
		model.addAttribute("noticeList", noticeList);
		System.out.println(noticeList);
		return "hospital/noticeModify";
	}
	
	/**
	 * 공지사항 수정 - PROCESS
	 * @param principal
	 * @param noticeVO
	 * @param param
	 * @param resp
	 */
	@PostMapping("/hospital/noticeUpdate")
	@ResponseBody
	public void noticeUpdate(Principal principal,
							   NoticeVO noticeVO,
							   @RequestParam Map<String, Object> param,
							   HttpServletResponse resp) {
		
		resp.setContentType("text/html; charset=UTF-8");

		String hospitalId = principal.getName();
		noticeVO.setHospitalId(hospitalId);
		
		int noticeNo = noticeVO.getNoticeNo();
		
		if (noticeVO.getUploadFiles()[0].isEmpty()) {
			hospitalService.updateNotice(noticeVO);
		} else {

			hospitalService.deleteAttachment(noticeNo);
			List<String> fileNames = fileUploadService.uploadFiles(noticeVO.getUploadFiles());

			// notice정보..
			hospitalService.updateNotice(noticeVO);

			// 파일정보...
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("chkList", fileNames);
			
			for (String name : fileNames) {
				noticeVO.setFileName(name);
				hospitalService.insertAttach(noticeVO);
			}
		}
		PrintWriter out;
		try {
			out = resp.getWriter();
			out.println("<script language='javascript'>");
			out.println("alert('수정을 성공했습니다.'); location.href='/hospital/noticeList';");
			out.println("</script>");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 공지사항 & 첨부파일 삭제
	 * @param principal
	 * @param noticeNo
	 * @return
	 */
	@ResponseStatus(HttpStatus.SEE_OTHER)
	@DeleteMapping("/hospital/noticeDelete/{noticeNo}")
	public String noticeDelete(Principal principal,
							   @PathVariable int noticeNo) {

	    String hospitalId = principal.getName();
	    
        NoticeVO noticeVO = new NoticeVO();
        noticeVO.setHospitalId(hospitalId);
        noticeVO.setNoticeNo(noticeNo);

	    // 첨부 파일 먼저 삭제
	    hospitalService.deleteAttachment(noticeNo);

	    // 공지사항 삭제
	    hospitalService.deleteNotice(noticeVO);

	    return "redirect:/hospital/noticeList";
	}
	
	//병원 수정,탈퇴 & 의사 등록,수정
	/**
	 * 병원 업데이트 - FORM
	 * @return
	 */
	@GetMapping("/hospital/hosinfoupdate")
	public String hosUpdateForm() {
		return "hospital/hosupdate";
	}
	
	/**
	 * 병원 업데이트 - PROCESS
	 * @param hVO
	 * @return
	 */
	@PostMapping("/hospital/hosinfoupdate")
	@ResponseBody
	public Map<String, Object> hosUpdate(HospitalVO hVO) {
		Map<String, Object> response = new HashMap<>();
		String password = hVO.getHospitalPw();
		password = bCryptPasswordEncoder.encode(password);
		hVO.setHospitalPw(password);

		if (hVO.getUploadFiles() != null) {
			List<String> imageList = fileUploadService.uploadFiles(hVO.getUploadFiles());

			String uploadedFileName = imageList.get(0); // 첫 번째 파일의 경로
			hVO.setHospitalImg(uploadedFileName);
		}

		try {
			int cnt = userMemService.updateHosInfo(hVO);
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
	 * 의사 등록 - FORM
	 * @return
	 */
	@GetMapping("/hospital/doctorinsert")
	public String doctorInsertForm() {
		return "hospital/doctorinsert";
	}

	/**
	 * 의사 등록 - PROCESS
	 * @param vo
	 * @param i1Times
	 * @param i2Times
	 * @param i3Times
	 * @param i4Times
	 * @param i5Times
	 * @param i6Times
	 * @param i7Times
	 * @return
	 */
	@PostMapping("/hospital/doctorinsert")
	@ResponseBody
	public Map<String, Object> doctorInsert(DoctorVO vo, @RequestParam("i1") List<String> i1Times,
			@RequestParam("i2") List<String> i2Times, @RequestParam("i3") List<String> i3Times,
			@RequestParam("i4") List<String> i4Times, @RequestParam("i5") List<String> i5Times,
			@RequestParam("i6") List<String> i6Times, @RequestParam("i7") List<String> i7Times) {

		Map<String, Object> response = new HashMap<>();

		List<DoctorTimeVO> times = new ArrayList<>();
		times.add(createDoctorTimeVO("i1", i1Times));
		times.add(createDoctorTimeVO("i2", i2Times));
		times.add(createDoctorTimeVO("i3", i3Times));
		times.add(createDoctorTimeVO("i4", i4Times));
		times.add(createDoctorTimeVO("i5", i5Times));
		times.add(createDoctorTimeVO("i6", i6Times));
		times.add(createDoctorTimeVO("i7", i7Times));

		vo.setTimes(times);
		
		try {
			int cnt = hospitalService.insertDoctor(vo);
			if (cnt > 0) {
				int doctorNo = hospitalService.getCurrentDoctorNo();
			    System.out.println(doctorNo);
				response.put("result", true);
				response.put("msg", "성공적으로 등록되었습니다.");
			    
			} else {
				response.put("result", false);
				response.put("msg", "등록에 실패했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("result", false);
			response.put("msg", "에러 발생");
		}

		return response;
	}

	private DoctorTimeVO createDoctorTimeVO(String day, List<String> times) {
		DoctorTimeVO doctorTimeVO = new DoctorTimeVO();
		doctorTimeVO.setDay(day);
		doctorTimeVO.setTimeArray(times);
		return doctorTimeVO;
	}
	
	/**
	 * 의사 정보 수정 - FORM
	 * @param doctorNo
	 * @param model
	 * @return
	 */
	@GetMapping("/hospital/doctorupdate/{DoctorNo}")
	public String doctorUpdateForm(@PathVariable("DoctorNo") String doctorNo, Model model) {
		int doctorNum = Integer.parseInt(doctorNo);
		DoctorVO drinfo = hospitalService.selectDoctor(doctorNum);
		model.addAttribute("drInfo", drinfo);
		return "hospital/doctormodify";
	}
	
	/**
	 * 의사 정보 수정 - PROCESS
	 * @param vo
	 * @param i1Times
	 * @param i2Times
	 * @param i3Times
	 * @param i4Times
	 * @param i5Times
	 * @param i6Times
	 * @param i7Times
	 * @return
	 */
	@PostMapping("/hospital/doctorupdate")
	@ResponseBody
	public Map<String, Object> doctorUpdate(DoctorVO vo, @RequestParam("i1") List<String> i1Times,
			@RequestParam("i2") List<String> i2Times, @RequestParam("i3") List<String> i3Times,
			@RequestParam("i4") List<String> i4Times, @RequestParam("i5") List<String> i5Times,
			@RequestParam("i6") List<String> i6Times, @RequestParam("i7") List<String> i7Times) {
		Map<String, Object> response = new HashMap<>();

		List<DoctorTimeVO> times = new ArrayList<>();
		times.add(createDoctorTimeVO("i1", i1Times));
		times.add(createDoctorTimeVO("i2", i2Times));
		times.add(createDoctorTimeVO("i3", i3Times));
		times.add(createDoctorTimeVO("i4", i4Times));
		times.add(createDoctorTimeVO("i5", i5Times));
		times.add(createDoctorTimeVO("i6", i6Times));
		times.add(createDoctorTimeVO("i7", i7Times));

		vo.setTimes(times);
		
		System.out.println(vo);
		
		try {
			int cnt = hospitalService.updateDoctor(vo);
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
	
}
