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

import com.drcome.project.FileUploadService;
import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.common.service.PageDTO2;
import com.drcome.project.medical.service.DoctorTimeVO;
import com.drcome.project.medical.service.DoctorVO;
import com.drcome.project.medical.service.HospitalService;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.medical.service.NoticeVO;
import com.drcome.project.mem.mapper.UserMemberMapper;
import com.drcome.project.mem.service.UserMemberService;
import com.drcome.project.mem.service.UserMemberVO;

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

	/* 공통 */
	// 공통 병원 정보 따로 빼기
	@ModelAttribute("hospitalSel")
	public Hospital getServerTime(Principal principal) {
		String hospitalId = principal.getName();
		Hospital hosSel = hospitalService.findByhospitalId(hospitalId);
		return hosSel;
	}

	/* 대시보드 */
	// today 진료 내역 및 예약 내역 리스트 -- 1
	// QNA 답변 -- 2
	@GetMapping(value = { "/hospital", "/hospitalHome" })
	public String home(Principal principal, String hospitalId, Model model) {
		hospitalId = principal.getName();
		List<Map<String, Object>> tolist = hospitalService.getTodayReserve(hospitalId);
		List<Map<String, Object>> QnAO = hospitalService.getQnAO(hospitalId);
		List<Map<String, Object>> QnAX = hospitalService.getQnAX(hospitalId);
		model.addAttribute("tolist", tolist);
		model.addAttribute("QnAO", QnAO);
		model.addAttribute("QnAX", QnAX);
		return "hospital/home";
	}
  
	/* 예약내역 - clinic */
	// Main
	@GetMapping("/hospital/clinicMain")
	public String clinicReserve(Principal principal, String hospitalId, String date, String reserveKindstatus,
			Model model) {
		hospitalId = principal.getName();
		List<Map<String, Object>> reserveList = hospitalService.getRerveList(hospitalId, date, reserveKindstatus);
		model.addAttribute("reserveList", reserveList);
		return "hospital/clinicMain";
	}

	// Dr
	@GetMapping("/hospital/clinicDr")
	public String clinicReserveDr(Principal principal, String hospitalId, Integer doctorNo, Model model) {
		hospitalId = principal.getName();
		doctorNo = 123;
		List<Map<String, Object>> reserveDrList = hospitalService.getReserveDrList(hospitalId, doctorNo);
		model.addAttribute("reserveDrList", reserveDrList);
		return "hospital/clinicDr";
	}

	/* 병원프로필 */
	// 병원 단건조회(id로) + 병원-의사 조회
	@GetMapping("/hospital/myProfile")
	public String findHospital(Principal principal, String hospitalId, Model model) {
		hospitalId = principal.getName();
		List<DoctorVO> docList = hospitalService.getDoctorAll(hospitalId);
		System.out.println(docList);
		model.addAttribute("docList", docList);
		return "hospital/myProfile";
	}

	/* 환자리스트 */
	// 병원 환자 전체 조회
	@GetMapping("/hospital/patientList")
	public String searchPatient(Principal principal, String hospitalId, Model model) {
		hospitalId = principal.getName();
		List<Map<String, Object>> palist = hospitalService.getPaientList(hospitalId);
		model.addAttribute("palist", palist);
		return "hospital/patientList";
	}

	// @RequestParam("userId") Long firstPageId
	// 병원 환자별 상세내역
	@GetMapping("/hospital/patientList/patientDetail")
	public String detailPatient(Principal principal, String hospitalId, Integer patientNo, Model model) {
		hospitalId = principal.getName();
		System.out.println("Received patientNo: " + patientNo);

		List<Map<String, Object>> detailPList = hospitalService.getPaientDetailList(hospitalId, patientNo);
		model.addAttribute("patientNo", patientNo);
		model.addAttribute("detailPList", detailPList);
		System.out.println(detailPList);
		return "hospital/patientDetail";
	}
	
	// 환자 진료내역 info
	@GetMapping("/hospital/patientList/patientDetail/patientClinicInfo")
	public String patientClinic(Principal principal, String hospitalId, Integer patientNo, Integer clinicNo, Model model) {
		hospitalId = principal.getName();
		
		Map<String, Object> patientInfo = hospitalService.getPaientClinicInfo(hospitalId, patientNo, clinicNo);
		List<Map<String, Object>> patientPill = hospitalService.getpaientPillInfo(clinicNo);
		
		model.addAttribute("patientNo", patientNo);
		model.addAttribute("clinicNo", clinicNo);
		model.addAttribute("patientInfo", patientInfo);
		model.addAttribute("patientPill", patientPill);
		System.out.println(patientInfo);
		System.out.println("Received clinicNo" + clinicNo);
		System.out.println(patientPill);
		return "hospital/patientClinicInfo";
	}

	/* 예약내역 - clinic */
	//Main
	@GetMapping("/hospital/clinicMain")
	public String clinicReserve(Principal principal, String hospitalId, String date, String reserveKindstatus) {
		hospitalId = principal.getName();
		return "hospital/clinicMain";
	}
	@GetMapping("/hospital/clinicMain/ajax")
	@ResponseBody
	public List<Map<String, Object>> clinicReserve1(Principal principal, String hospitalId, String date, String reserveKindstatus) {
		hospitalId = principal.getName();
		List<Map<String, Object>> reserveList = hospitalService.getRerveList(hospitalId, date, reserveKindstatus);
		return reserveList;
	}
	
	//Dr
	@GetMapping("/hospital/clinicDr")
	public String clinicReserveDr(Principal principal, String hospitalId, Integer doctorNo, String date, String reserveKindstatus) {
		hospitalId = principal.getName();
		return "hospital/clinicDr";
	}
	
	@GetMapping("/hospital/clinicDr/ajax")
	@ResponseBody
	public List<Map<String, Object>> clinicReserveDr1(Principal principal, String hospitalId, Integer doctorNo, String date, String reserveKindstatus, Model model) {
		hospitalId = principal.getName();
		List<Map<String, Object>> reserveDrList = hospitalService.getReserveDrList(hospitalId, doctorNo, date, reserveKindstatus);
		return reserveDrList;
	}
	@GetMapping("/hospital/clinicDr/allDr")
	@ResponseBody
	public List<Map<String, Object>> allDrList(Principal principal, String hospitalId) {
		hospitalId = principal.getName();
		List<Map<String, Object>> DrAllList = hospitalService.getDrAllList(hospitalId);
		return DrAllList;
	}
	/* 병원프로필 */
	// 병원 단건조회(id로) + 병원-의사 조회
	@GetMapping("/hospital/myProfile")
	public String findHospital(Principal principal, String hospitalId, Model model) {
		hospitalId = principal.getName();
		List<DoctorVO> docList = hospitalService.getDoctorAll(hospitalId);
		System.out.println(docList);
		model.addAttribute("docList", docList);
		return "hospital/myProfile";
	}

	/* QnA */
	// QnA 전체
	@GetMapping("/hospital/qnaList")
	public String qnaList(Principal principal, String hospitalId, Model model) {
		hospitalId = principal.getName();
		List<Map<String, Object>> qnaAllList = hospitalService.getQnaList(hospitalId);
		model.addAttribute("qnaAllList", qnaAllList);
		return "hospital/qnaList";
	}

	// QnA 단건상세
	@GetMapping("/hospital/qnaList/qnaDetail")
	public String qnaInfo(Principal principal, String hospitalId, Integer qnaNo, Model model) {
		hospitalId = principal.getName();
		List<Map<String, Object>> qnaInfo = hospitalService.getQnaInfo(hospitalId, qnaNo);
		model.addAttribute("qnaInfo", qnaInfo);
		model.addAttribute("qnaNo", qnaNo);
		return "hospital/qnaDetail";
	}

	/* 공지사항 */
	// 공지사항 불러오기
	@GetMapping("/hospital/noticeList")
	public String qnaList(Principal principal, String hospitalId) {
		hospitalId = principal.getName();

		return "hospital/noticeList";
	}

	// 공지사항 전체 - 페이징
	@GetMapping("/hospital/noticeListP")
	@ResponseBody
	public Map<String, Object> noticeList(Principal principal, String hospitalId,
			@RequestParam(required = false, defaultValue = "1") String page) {
		hospitalId = principal.getName();

		Map<String, Object> map = new HashMap();
		// 리스트 전체 개수
		int total = hospitalService.noticeCount(hospitalId);

		// 선택 페이지 변환
		int cpage = Integer.parseInt(page);
		System.out.println("선택된 페이지" + cpage);

		// 페이지네이션(currentpage, total)
		PageDTO2 dto = new PageDTO2(cpage, total);
		System.out.println("dtd 객체" + dto);

		List<Map<String, Object>> plist = hospitalService.getNoticeList(cpage, hospitalId);

		System.out.println(plist.size());

		// ajax는 return으로...

		map.put("plist", plist);
		map.put("pagedto", dto);

		return map;
	}

	// 공지사항 단건상세
	@GetMapping("/hospital/noticeList/noticeDetail")
	public String noticeDetail(Principal principal, String hospitalId, Integer noticeNo, Model model) {
		hospitalId = principal.getName();
		List<NoticeVO> noticeList = hospitalService.getNoticeDetail(hospitalId, noticeNo);
//		List<Map<String, Object>> noticeInfo = hospitalService.getNoticeDetail(hospitalId, noticeNo);
		model.addAttribute("noticeNo", noticeNo);
		model.addAttribute("noticeList", noticeList);
		return "hospital/noticeDetail";
	}

	// 공지사항 등록 - FORM
	@GetMapping("/hospital/noticeForm")
	public String insertEmpInfoForm() {
		return "hospital/noticeForm";
	}

	// 공지사항 등록 - PROCESS
	@PostMapping("/hospital/noticeForm")
	public void insertNoticeInfoProcess(Principal principal, NoticeVO vo, HttpServletResponse resp,
			HttpServletRequest request) {

		resp.setContentType("text/html; charset=UTF-8");

		String hospitalId = principal.getName();
		vo.setHospitalId(hospitalId);

		System.out.println("이미지테스트랭thㅡ" + vo.getUploadFiles().length);
		System.out.println("이미지테스트" + vo.getUploadFiles());
		try {
			System.out.println(vo.getUploadFiles()[0].isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (vo.getUploadFiles()[0].isEmpty()) {
			hospitalService.insertNoticeInfo(vo);
		} else {

			List<String> fileNames = fileUploadService.uploadFiles(vo.getUploadFiles());

			// notice정보..
			hospitalService.insertNoticeInfo(vo);

			// 파일정보...
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("chkList", fileNames);

			System.out.println(vo.getNoticeNo());

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
  
	@GetMapping("/hospital/hosinfoupdate")
	public String hosUpdateForm() {
		return "hospital/hosupdate";
	}

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

		System.out.println(hVO);

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

	@GetMapping("/hospital/doctorinsert")
	public String doctorInsertForm() {
		return "hospital/doctorinsert";
	}

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
	
	@GetMapping("/hospital/doctorupdate/{DoctorNo}")
	public String doctorUpdateForm(@PathVariable("DoctorNo") String doctorNo, Model model) {
		int doctorNum = Integer.parseInt(doctorNo);
		DoctorVO drinfo = hospitalService.selectDoctor(doctorNum);
		model.addAttribute("drInfo", drinfo);
		return "hospital/doctormodify";
	}
	
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
	
	// 공지사항 검색
//	@GetMapping("/hospital/searchNotices")
//	@ResponseBody
//	public Map<String, Object> searchNotices(Principal principal,
//	                         @RequestParam("type") int type,
//	                         @RequestParam("keyword") String keyword,
//	                         @RequestParam(required = false, defaultValue = "1") String page) {
//	    String hospitalId = principal.getName(); // Principal 객체에서 병원 ID 가져오기
//	    
//	    Map<String, Object> map = new HashMap();
//	    // 리스트 전체 개수
//	    int total = hospitalService.searchNoticeCount(hospitalId, keyword);
//	    
//	    // 선택 페이지 변환
//	    int cpage = Integer.parseInt(page);
//	    System.out.println("선택된 페이지" + cpage);
//	    
//	    // 페이지네이션(currentpage, total)
//	    PageDTO2 dto = new PageDTO2(cpage, total);
//	    System.out.println("dtd 객체" + dto);
//	    
//	    // 검색된 공지사항 리스트 가져오기
//	    List<NoticeVO> searchResults = hospitalService.searchNotice(type, keyword, hospitalId);
//	    
//	    // 결과를 담을 Map 생성
//	    map.put("searchResults", searchResults);
//	    map.put("pagedto", dto);
//	    
//	    return map;
//	}

	
	//공지사항 검색
    @GetMapping("/hospital/searchNotices")
    @ResponseBody
    public List<NoticeVO> searchNotices(Principal principal,
    							String hospitalId,
    							@RequestParam("type") int type,
    							@RequestParam("keyword") String keyword,
    							Model model) {
    	hospitalId = principal.getName();
        List<NoticeVO> searchResults = hospitalService.searchNotice(type, keyword, hospitalId);
        model.addAttribute("searchResults", searchResults);
        System.out.println(searchResults);
        // JSON으로 반환
        return searchResults;
    }
}
