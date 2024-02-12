package com.drcome.project.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.admin.domain.Pharmacy;
import com.drcome.project.admin.domain.Usertable;
import com.drcome.project.admin.repository.HospitalListRepository;
import com.drcome.project.admin.repository.PharmacyRepository;
import com.drcome.project.admin.repository.UsertableRepository;
import com.drcome.project.admin.service.AdminService;

@Controller
public class AdminController {
	
	@Autowired
	UsertableRepository urepo;
   
	@Autowired
	HospitalListRepository hrepo;
   
	@Autowired
	PharmacyRepository prepo;
   
	@Autowired
	AdminService aservice;
   
   @GetMapping("/admin")
   public String home(@RequestParam(required = false, defaultValue = "0") int pageNo, Model model) {
      Page<Hospital> grantlisth = aservice.findByhospitalStatus("b1", pageNo, 5);
      model.addAttribute("grantlisth", grantlisth);
      
      Page<Pharmacy> grantlistp = aservice.findBypharmacyStatus("b1", pageNo, 5);
      model.addAttribute("grantlistp", grantlistp);
      return "admin/home";
   }
   
   /* 일반 사용자 */
   @GetMapping("/admin/user")
   public String user(@RequestParam(required = false, defaultValue = "0") int pageNo, Model model) {
      Page<Usertable> userlist = aservice.getuserAll(pageNo, 10);
      model.addAttribute("list", userlist);
      
		/*
		 * Page<Usertable> userlistc = aservice.getgeneraluser("a1", pageNo, 10);
		 * model.addAttribute("clist", userlistc);
		 * 
		 * Page<Usertable> userlistw = aservice.getwithdrawaluser("a3", pageNo, 10);
		 * model.addAttribute("wlist", userlistw);
		 */
     
      return "admin/adminUser";
   }
   
   /* 일반 사용자 - 일반회원 */
   @GetMapping("/admin/userc")
   public String commonuser(@RequestParam(required = false, defaultValue = "0") int pageNo, Model model) {
      Page<Usertable> userlist = aservice.getgeneraluser("a1", pageNo, 10);
      model.addAttribute("clist", userlist);
      return "admin/adminUser";
   }
   
   /* 일반 사용자 - 탈퇴회원 */
   @GetMapping("/admin/userw")
   public String withdrawaluser(@RequestParam(required = false, defaultValue = "0") int pageNo, Model model) {
      Page<Usertable> userlist = aservice.getwithdrawaluser("a3", pageNo, 10);
      model.addAttribute("wlist", userlist);
      return "admin/adminUser";
   }
   
   /* 승인된 병원 사용자 */
   @GetMapping("/admin/hospital")
   public String hospital(@RequestParam(required = false, defaultValue = "0") int pageNo, Model model) {
      Page<Hospital> hospitallist = aservice.findByhospitalStatus("b2", pageNo, 10);
      model.addAttribute("list", hospitallist);
      return "admin/adminHospital";
   }
   /* 병원 사용자 승인 대기 리스트 */
   @GetMapping("/admin/hospital/grant")
   public String findByhospitalStatus(@RequestParam(required = false, defaultValue = "0") int pageNo, Model model) {
      Page<Hospital> grantlist = aservice.findByhospitalStatus("b1", pageNo, 5);
      model.addAttribute("grantlisth", grantlist);
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
   public String pharmacy(@RequestParam(required = false, defaultValue = "0") int pageNo, Model model) {
      Page<Pharmacy> pharmacylist = aservice.findBypharmacyStatus("b2", pageNo, 10);
      model.addAttribute("list", pharmacylist);
      return "admin/adminPharmacy";
   }
   
   /* 약국 사용자 승인 대기 리스트 */
   @GetMapping("/admin/pharmacy/grant")
   public String findBypharmacyStatus(@RequestParam(required = false, defaultValue = "0") int pageNo, Model model) {
      Page<Pharmacy> grantlist = aservice.findBypharmacyStatus("b1", pageNo, 5);
      model.addAttribute("grantlistp", grantlist);
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