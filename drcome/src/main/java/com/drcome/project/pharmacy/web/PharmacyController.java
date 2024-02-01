package com.drcome.project.pharmacy.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

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
		String pharmacyId = "pharmacy1";
		pharmacyVO.setPharmacyId(pharmacyId);
		PharmacyVO findVO = pservice.selectPharmacyInfo(pharmacyVO);
		model.addAttribute("pinfo", findVO);
		return "pharmacy/pharmacyInfo";
	}
	
	@GetMapping("/pharmacy/status")
	public String pharmacyList(String pharmacyId, Model model) {
		pharmacyId = "pharmacy1";
		List<Map<String, Object>> plist = pservice.selectPrescriptionList(pharmacyId);
		model.addAttribute("plist", plist);
		return "pharmacy/perscriptionStatus";
	}
	
//	/*
//	 * @GetMapping("/pharmacy/medicine") public String findMedicine(String keyword,
//	 * Model model) { return "pharmacy/pharmacySearch";
//	 * 
//	 * }
//	 */
	
	@ModelAttribute("pharmacy")
	public PharmacyVO getServer() {
		PharmacyVO pharmacyVO = new PharmacyVO();
		String pharmacyId = "pharmacy1";
		pharmacyVO.setPharmacyId(pharmacyId);
		PharmacyVO findVO = pservice.selectPharmacyInfo(pharmacyVO);
		return findVO;
	}
}
