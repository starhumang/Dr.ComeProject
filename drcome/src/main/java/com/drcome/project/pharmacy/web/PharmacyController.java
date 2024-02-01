package com.drcome.project.pharmacy.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.drcome.project.pharmacy.PharmacyVO;
import com.drcome.project.pharmacy.service.PharmacyService;

@Controller
public class PharmacyController {
	
	@Autowired
	PharmacyService pservice;

	@GetMapping("/pharmacy")
	public String home() {
		return "pharmacy/home";
	}
	
	@GetMapping("/pharmacy/info")
	public String pharmacy(PharmacyVO pharmacyVO, Model model) {
		PharmacyVO findVO = pservice.selectPharmacyInfo(pharmacyVO);
		model.addAttribute("pinfo", findVO);
		return "pharmacy/pharmacyInfo";
	}
	
	@GetMapping("/pharmacy/status")
	public String pharmacyList(Model model) {
		List<Map<String, Object>> plist = pservice.selectPrescriptionList();
		System.out.println(plist);
		model.addAttribute("plist", plist);
		return "pharmacy/perscriptionStatus";
		
	}
}
