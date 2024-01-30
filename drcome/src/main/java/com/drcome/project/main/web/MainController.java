package com.drcome.project.main.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.drcome.project.main.service.MainService;
import com.drcome.project.medical.HospitalVO;

@Controller
public class MainController {
	
	@Autowired
	MainService mainService;
	
	//병원목록
	@GetMapping("hosList")
	public String getHosList(Model model) {
		List<HospitalVO> list = mainService.getHosList();
		model.addAttribute("hosList", list);
		System.out.println(list);
		return "user/home";
	}
	
	//약국목록
//		@GetMapping("phaList")
//		public String getPhaList(Model model) {
//			List<PharmacyVO> list = mainService.getPhaList();
//			model.addAttribute("phaList", list);
//			return "/";
//		}

}
