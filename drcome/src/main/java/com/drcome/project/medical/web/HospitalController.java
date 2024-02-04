package com.drcome.project.medical.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.medical.service.DoctorVO;
import com.drcome.project.medical.service.HospitalService;

@Controller
public class HospitalController {
	@Autowired
	//HospitalRepository repo;
	HospitalService hospitalService;
	
	/* 공통 */
	//공통 병원 정보 따로 빼기
	@ModelAttribute("hospitalSel")
	public Hospital getServerTime(Principal principal) {
		String hospitalId = principal.getName();
	    Hospital hosSel = hospitalService.findByhospitalId(hospitalId);
	    return hosSel;
	}	
	
	/* 대시보드 */
	//today 진료 내역 및 예약 내역 리스트 -- 1
	//QNA 답변 -- 2
	@GetMapping(value = {"/hospital", "/hospitalHome"})
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
	//메인
	@GetMapping("/hospital/clinicMain")
	public String clinicReserve() {
		return "hospital/clinicMain";
	}
	
	/* 병원프로필 */
	//병원 단건조회(id로) + 병원-의사 조회
	@GetMapping("/hospital/myProfile")
	public String findHospital(Principal principal, String hospitalId, Model model) {
		hospitalId = principal.getName();
		List<DoctorVO> docList = hospitalService.getDoctorAll(hospitalId);
		System.out.println(docList);
		model.addAttribute("docList", docList);
		return "hospital/myProfile";
	}
	
	/* 환자리스트 */
	//병원 환자 전체 조회
	@GetMapping("/hospital/patientList")
	public String searchPatient(Principal principal, String hospitalId, Model model) {
		hospitalId = principal.getName();
		List<Map<String, Object>> palist = hospitalService.getPaientList(hospitalId);
		model.addAttribute("palist", palist);
		return "hospital/patientList";
	}

	//@RequestParam("userId") Long firstPageId
	//병원 환자별 상세내역
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
	//QnA 전체
	@GetMapping("/hospital/qnaList")
	public String qnaList(Principal principal, String hospitalId, Model model) {
		hospitalId = principal.getName();
		List<Map<String, Object>> qnaAllList = hospitalService.getQnaList(hospitalId);
		model.addAttribute("qnaAllList", qnaAllList);
		return "hospital/qnaList";
	}
}
