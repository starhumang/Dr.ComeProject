package com.drcome.project.doctor.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.doctor.service.PatientService;
import com.drcome.project.doctor.service.PatientVO;
import com.drcome.project.medical.service.HospitalService;


@Controller
public class PatientController {
	
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

	@Autowired
	PatientService patientService;
	
	//비대면진료페이지
	@GetMapping("untactClinic")
	public String getUntactInfo(PatientVO vo, Model model) {
		
		//기본정보  //reserve no 받아오기 
		vo.setReserveNo(1);
		PatientVO findVO = patientService.getPatientInfo(vo);
		System.out.println(findVO);
		model.addAttribute("pInfo" , findVO);
		
		
		//진료기록리스트 //hid uid 받아오기....하
		List <PatientVO> clinicList = patientService.getClinicList("krrlo" , "user1");
		model.addAttribute("clist" , clinicList);
		System.out.println(clinicList);
		return "doctor/untactClinic";
		
		
	}
	
	//대면진료페이지
	@GetMapping("clinic")
	public String getInfo(PatientVO vo, Model model) {
		PatientVO findVO = patientService.getPatientInfo(vo);
		model.addAttribute("pInfo" , findVO);
		System.out.println(findVO);
		return "doctor/clinic";
		
	}
	
	//처방전 조회 
	@GetMapping("perscription/{no}")
	@ResponseBody
	public List<PatientVO> perscriptionInfo(@PathVariable Integer no) {
		PatientVO vo = new PatientVO();
		vo.setClinicNo(no);
		return patientService.getPerscription(vo);
	}
	
	
	
	
}
