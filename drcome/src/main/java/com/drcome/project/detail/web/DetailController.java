package com.drcome.project.detail.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.drcome.project.detail.service.DetailService;
import com.drcome.project.medical.service.DoctorVO;
import com.drcome.project.medical.service.HospitalService;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

@Controller
public class DetailController {
	
	@Autowired
	DetailService detailService;
	@Autowired
	HospitalService hospitalService;
	
	//병원 상세페이지
		@GetMapping("/hospitalDetail")
		public String hosInformation(String hospitalId, Model model) { //String hospitalId 이게 get으로 링크에서 받은 값
			HospitalVO hosInfo = detailService.getHos(hospitalId);
			//System.out.println("hosInfo"+ hosInfo);
			model.addAttribute("hosInfo", hosInfo);
			List<DoctorVO> docList = hospitalService.getDoctorAll(hospitalId); //의사정보가져오는거
			//System.out.println(docList);
			model.addAttribute("docList", docList);
			return "user/hosDetail";
		}
		
	//약국 상세페이지
		@GetMapping("/pharmacyDetail")
		public String phaInformation(String pharmacyId, Model model) {
			PharmacyVO phaInfo = detailService.getPha(pharmacyId);
			//System.out.println("phaInfo" + phaInfo);
			model.addAttribute("phaInfo", phaInfo);
			return "user/phaDetail";
		}
}
