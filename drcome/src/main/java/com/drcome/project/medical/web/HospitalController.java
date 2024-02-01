package com.drcome.project.medical.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.medical.service.DoctorVO;
import com.drcome.project.medical.service.HospitalService;

@Controller
public class HospitalController {
	@Autowired
//	HospitalRepository repo;
	HospitalService hospitalService;
	
	//공통 병원 정보 따로 빼기
	@ModelAttribute("hospitalSel")
	public Hospital getServerTime() {
		String hospitalId = "krrlo";
	    Hospital hosSel = hospitalService.findByhospitalId(hospitalId);
	    return hosSel;
	}	
	
	//병원 마이페이지 메인-대시보드
	@GetMapping(value = {"/hospital", "/hospitalHome"})
	public String home() {
		return "/hospital/home";
	}

	//병원 마이프로필
	//병원 단건조회(id로) + 병원-의사 조회
	@GetMapping("/hospital/myProfile")
	public String findHospital(String hospitalId, Model model) {
		hospitalId = "krrlo";
		List<DoctorVO> docList = hospitalService.getDoctorAll(hospitalId);
		System.out.println(docList);
		model.addAttribute("docList", docList);
		return "hospital/myprofile";
	}
	
	//병원 환자 전체 조회
	@GetMapping("/hospital/patientList")
	public String searchPatient(String hospitalId, Model model) {
		hospitalId = "krrlo";
		List<Map<String, Object>> palist = hospitalService.getPaientList(hospitalId);
		model.addAttribute("palist", palist);
		return "hospital/patientList";
	}
	//@RequestParam("userId") Long firstPageId
	//병원 환자별 상세내역
	@GetMapping("/hospital/patientList/patientDetail")
	public String detailPatient(String hospitalId, Integer patientNo, Model model) {
		hospitalId = "krrlo";
		System.out.println("Received patientNo: " + patientNo);
		
		List<Map<String, Object>> detailPList = hospitalService.getPaientDetailList(hospitalId, patientNo);
		model.addAttribute("patientNo", patientNo);
		model.addAttribute("detailPList", detailPList);
		System.out.println(detailPList);
		return "hospital/patientDetail";
	}
}
