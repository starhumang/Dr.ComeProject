package com.drcome.project.medical.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.drcome.project.FileUploadService;
import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.medical.service.DoctorVO;
import com.drcome.project.medical.service.HospitalService;
import com.drcome.project.medical.service.NoticeVO;

@Controller
public class HospitalController {
	@Autowired
	// HospitalRepository repo;
	HospitalService hospitalService;

	@Autowired
	private FileUploadService fileUploadService;

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

	/* 진료및예약 */
	// 메인
	@GetMapping("/hospital/clinicMain")
	public String clinicReserve() {
		return "hospital/clinicMain";
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
	// 공지사항 전체
	@GetMapping("/hospital/noticeList")
	public String noticeList(Principal principal, String hospitalId, Model model) {
		hospitalId = principal.getName();
		List<Map<String, Object>> noticeAllList = hospitalService.getNoticeList(hospitalId);
		model.addAttribute("noticeAllList", noticeAllList);
		return "hospital/noticeList";
	}

	// 공지사항 단건상세
	@GetMapping("/hospital/noticeList/noticeDetail")
	public String noticeDetail(Principal principal, String hospitalId, Integer noticeNo, Model model) {
		hospitalId = principal.getName();
		List<Map<String, Object>> noticeInfo = hospitalService.getNoticeDetail(hospitalId, noticeNo);
		model.addAttribute("noticeNo", noticeNo);
		model.addAttribute("noticeInfo", noticeInfo);
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
}
