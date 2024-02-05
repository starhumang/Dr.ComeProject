package com.drcome.project.pharmacy.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.drcome.project.pharmacy.PharmacySelectVO;
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
	public String pharmacyprint(String date, String pharmacyId, Model model) {
		pharmacyId = "pharmacy1";
		/*
		 * List<Map<String, Object>> plist = pservice.selectPrescriptionList(date,
		 * pharmacyId); model.addAttribute("plist", plist);
		 */
		return "pharmacy/perscriptionStatus";
	}

	

	@GetMapping("/pharmacy/status/{date}")
	@ResponseBody
	public List<Map<String, Object>> pharmacyList(@PathVariable String date, String pharmacyId, Model model) {
		pharmacyId = "pharmacy1";
		List<Map<String, Object>> plist = pservice.selectPrescriptionList(date, pharmacyId);
		model.addAttribute("plist", plist);
		return pservice.selectPrescriptionList(date, pharmacyId);
	}

	@PostMapping("/pharmacy/rejection")
	@ResponseBody
	public Map<String, Object> updaterejection(@SessionAttribute(name = "userId", required = false) String id, PharmacySelectVO pharmacyselectVO) {
		System.out.println(pharmacyselectVO);
		pharmacyselectVO.setPharmacyId(id);
		return pservice.updaterejection(pharmacyselectVO);
	}

	@ModelAttribute("pharmacy")
	public PharmacyVO getServer() {
		PharmacyVO pharmacyVO = new PharmacyVO();
		String pharmacyId = "pharmacy1";
		pharmacyVO.setPharmacyId(pharmacyId);
		PharmacyVO findVO = pservice.selectPharmacyInfo(pharmacyVO);
		return findVO;
	}
}
