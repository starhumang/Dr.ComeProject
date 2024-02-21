package com.drcome.project.admin.web;

import java.util.List;

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

/** 사이트 관리자 페이지(일반 사용자, 병원 사용자, 약국 사용자 조회 및 승인 처리)
 * 
 * @author 최해주
 * @since 2024.01
 *
 */

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
   
	/**
	 * 관리자 대쉬보드 페이지
	 * @param pageNo 페이징
	 * @param model 화면모델
	 * @return admin/home
	 */
   @GetMapping("/admin")
   public String home(@RequestParam(required = false, defaultValue = "0") int pageNo, 
		   			  Model model, 
		   			  String ustatus) {
		
	    // 일반 사용자 cnt
		Page<Usertable> userlist = aservice.getuserAll("a1", pageNo, 10);
		model.addAttribute("totalCount", userlist.getTotalElements());

		// 병원 승인 리스트&병원 승인 cnt
		Page<Hospital> grantlisth = aservice.findByhospitalStatus("b1", pageNo, 5);
		model.addAttribute("grantlisth", grantlisth);
		long h = grantlisth.getTotalElements();
		
		// 약국 승인 리스트&병원 승인 cnt
		Page<Pharmacy> grantlistp = aservice.findBypharmacyStatus("b1", pageNo, 5);
		model.addAttribute("grantlistp", grantlistp);
		long p = grantlistp.getTotalElements();
		long total = h + p;
		model.addAttribute("total", total);
		
		// 병원 사용자 cnt
		Page<Hospital> hospitallist = aservice.findByhospitalStatus("b2", pageNo, 10);
		model.addAttribute("totalCounth", hospitallist.getTotalElements());
		
		// 약국 사용자 cnt
		Page<Pharmacy> pharmacylist = aservice.findBypharmacyStatus("b2", pageNo, 10);
		model.addAttribute("totalCountp", pharmacylist.getTotalElements());
		
      
		return "admin/home";
   }
   
     /**
      * 일반 사용자 목록 조회
      * @param userStatus 
      * @param pageNo
      * @param model
      * @return
      */
	 @GetMapping("/admin/user") 
	 public String getUsers(String userStatus,
			 				@RequestParam(defaultValue = "0") int pageNo, 
			 				Model model) { 
		 Page<Usertable> userlist = aservice.getuserAll(userStatus, pageNo, 10);
		 model.addAttribute("list", userlist); 
		 return "admin/adminUser"; 
	}

   /**
    * 병원 사용자 목록 조회(승인된 항목)
    * @param pageNo 페이징 조건
    * @param model 화면 모델
    * @return admin/adminHospital
    */
   @GetMapping("/admin/hospital")
   public String hospital(@RequestParam(defaultValue = "b2") String hospitalStatus,
		   				  @RequestParam(required = false, defaultValue = "0") int pageNo, 
		   				  Model model) {
      Page<Hospital> hospitallist = aservice.findByhospitalStatus(hospitalStatus, pageNo, 10);
      model.addAttribute("clist", hospitallist);
      return "admin/adminHospital";
   }

   /**
    * 병원 사용자 승인 대기 목록 조회
    * @param pageNo 페이징 조건
    * @param model 화면 모델
    * @return admin/adminHospitalGrant
    */
   @GetMapping("/admin/hospital/grant")
   public String findByhospitalStatus(@RequestParam(required = false, defaultValue = "0") int pageNo, Model model) {
      Page<Hospital> grantlist = aservice.findByhospitalStatus("b1", pageNo, 5);
      model.addAttribute("grantlisth", grantlist);
      return "admin/adminHospitalGrant";
   }
   
   /**
    * 병원 사용자 승인 처리
    * @param HospitalId 수정 조건
    * @return admin/adminHospitalGrant
    */
   @PostMapping("/admin/hospital/grant/{HospitalId}")
   @ResponseBody
   public Hospital updateStatus(@PathVariable String HospitalId) {
      Hospital count = aservice.updateStatus(HospitalId);
      return count;
   }

   
   /**
    * 약국 사용자 목록 조회(승인된 항목)
    * @param pageNo 페이징 조건
    * @param model 화면 모델
    * @return admin/adminPharmacy
    */
   @GetMapping("/admin/pharmacy")
   public String pharmacy(@RequestParam(defaultValue = "b2") String pharmacyStatus, 
		   				  @RequestParam(required = false, defaultValue = "0") int pageNo,
		   				  Model model) {
      Page<Pharmacy> pharmacylist = aservice.findBypharmacyStatus(pharmacyStatus, pageNo, 10);
      model.addAttribute("list", pharmacylist);
      return "admin/adminPharmacy";
   }
   
   /**
    * 약국 사용자 승인 대기 목록 조회
    * @param pageNo 페이징 조건
    * @param model 화면 모델
    * @return admin/adminPharmacyGrant
    */
   @GetMapping("/admin/pharmacy/grant")
   public String findBypharmacyStatus(@RequestParam(required = false, defaultValue = "0") int pageNo, Model model) {
      Page<Pharmacy> grantlist = aservice.findBypharmacyStatus("b1", pageNo, 5);
      model.addAttribute("grantlistp", grantlist);
      return "admin/adminPharmacyGrant";
   }
   
   /**
    * 약국 사용자 승인 처리
    * @param pharmacyId 수정 조건
    * @return admin/adminPharmacyGrant
    */
   /* 약국 사용자 승인 처리*/
   @PostMapping("/admin/pharmacy/grant/{pharmacyId}")
   @ResponseBody
   public Pharmacy updatePharmacyStatus(@PathVariable String pharmacyId) {
      Pharmacy count = aservice.updatePharmacyStatus(pharmacyId);
      return count;
   }

}