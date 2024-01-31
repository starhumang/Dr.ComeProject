package com.drcome.project.medical.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.drcome.project.medical.domain.Hospital;
import com.drcome.project.medical.service.DoctorVO;
import com.drcome.project.medical.service.HospitalService;

@Controller
public class HospitalController {
	@Autowired
//	HospitalRepository repo;
	HospitalService hospitalService;
	
	@GetMapping(value = {"/hospital", "/hospitalHome"})
	public String home() {
		return "/hospital/home";
	}
	
//	@GetMapping("/hospital/myProfile")
//	public String hospital(Model model) {
//		Iterable<Hospital> hosList = repo.findAll();
//		model.addAttribute("hospitalList", hosList);
//		return "hospital/myprofile";
//	}
	
	//병원 프로필
	//병원 단건조회(id로) + 병원-의사 조회
	@GetMapping("/hospital/myProfile/{hospitalId}")
	public String findHospital(@PathVariable String hospitalId, Model model) {
		Hospital hosSel = hospitalService.findByhospitalId(hospitalId);
		model.addAttribute("hospitalSel", hosSel);
		
		List<DoctorVO> docList = hospitalService.getDoctorAll(hospitalId);
		System.out.println(docList);
		model.addAttribute("docList", docList);
		return "hospital/myprofile";
	}
	
	//병원 환자 전체 조회
	
	
}
