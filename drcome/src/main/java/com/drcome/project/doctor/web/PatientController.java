package com.drcome.project.doctor.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.drcome.project.doctor.service.PatientService;
import com.drcome.project.doctor.service.PatientVO;

@Controller
public class PatientController {

	@Autowired
	PatientService patientService;
	
	//비대면진료페이지
	@GetMapping("UntactClinic")
	public String getUntactInfo(PatientVO vo, Model model) {
		PatientVO findVO = patientService.getPatientInfo(vo);
		model.addAttribute("pInfo" , findVO);
		System.out.println(findVO);
		return "doctor/UntactClinic";
		
		
	}
	
	//대면진료페이지
	@GetMapping("Clinic")
	public String getInfo(PatientVO vo, Model model) {
		PatientVO findVO = patientService.getPatientInfo(vo);
		model.addAttribute("pInfo" , findVO);
		System.out.println(findVO);
		return "doctor/Clinic";
		
		
	}
	
	
	
}
