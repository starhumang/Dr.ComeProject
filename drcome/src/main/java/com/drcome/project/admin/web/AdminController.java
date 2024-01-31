package com.drcome.project.admin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.admin.domain.Pharmacy;
import com.drcome.project.admin.domain.Usertable;
import com.drcome.project.admin.repository.HospitalRepository;
import com.drcome.project.admin.repository.PharmacyRepository;
import com.drcome.project.admin.repository.UsertableRepository;
import com.drcome.project.admin.service.AdminService;

@Controller
public class AdminController {

	@Autowired
	UsertableRepository urepo;
	
	@Autowired
	HospitalRepository hrepo;
	
	@Autowired
	PharmacyRepository prepo;
	
	@Autowired
	AdminService aservice;
	
	@GetMapping("/admin")
	public String home() {
		return "admin/home";
	}
	
	/* 일반 사용자 */
	@GetMapping("/admin/user")
	//@ResponseBody //restController일 땐 안 써도 됨
	public String user(Model model) {
		Iterable<Usertable> userlist = urepo.findAll();
		model.addAttribute("list", userlist);
		return "admin/adminUser";
	}
	
	/* 병원 사용자 */
	@GetMapping("/admin/hospital")
	public String hospital(Model model) {
		Iterable<Hospital> hospitallist = hrepo.findAll();
		model.addAttribute("list", hospitallist);
		return "admin/adminHospital";
	}
	/* 병원 사용자 승인 리스트 */
	@GetMapping("/admin/hospital/grant")
	public String findByhospitalStatus(Model model) {
		List<Hospital> grantlist = hrepo.findAll();
		model.addAttribute("grantlist", grantlist);
		return "admin/adminHospitalGrant";
	}
	
	/* 병원 사용자 승인 처리*/
	@PostMapping("/admin/hospital/grant/{HospitalId}")
	@ResponseBody
	public String updateStatus(@PathVariable String HospitalId) {
		Hospital count = aservice.updateStatus(HospitalId);
		System.out.println(count);
		return "admin/adminHospitalGrant";
	}
	
	/* 약국 사용자 */
	@GetMapping("/admin/pharmacy")
	public String pharmacy(Model model) {
		Iterable<Pharmacy> pharmacylist = prepo.findAll();
		model.addAttribute("list", pharmacylist);
		return "admin/adminPharmacy";
	}
	
	/* 약국 사용자 승인 업무 */
	@GetMapping("/admin/pharmacy/grant")
	public String findBypharmacyStatus(Model model) {
		Iterable<Pharmacy> grantlist = prepo.findAll();
		model.addAttribute("grantlist", grantlist);
		return "admin/adminPharmacyGrant";
	}
	
	/* 약국 사용자 승인 처리*/
	@PostMapping("/admin/pharmacy/grant/{pharmacyId}")
	@ResponseBody
	public String updatePharmacyStatus(@PathVariable String pharmacyId) {
		Pharmacy count = aservice.updatePharmacyStatus(pharmacyId);
		System.out.println(count);
		return "admin/adminPharmacyGrant";
	}
	
}