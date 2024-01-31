package com.drcome.project.main.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.drcome.project.main.service.MainService;
import com.drcome.project.medical.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

@Controller
public class MainController {
	
	@Autowired
	MainService mainService;
	
	//병원목록
	@GetMapping(value ={"/", "/home"})//주소
	public String getHosList(Model model) {
		List<HospitalVO> hosList = mainService.getHosList();
		model.addAttribute("hosList", hosList);
		List<PharmacyVO> phaList = mainService.getPhaList();
		model.addAttribute("phaList", phaList);
		System.out.println(phaList);
		return "user/home";//폴더밑에 html 이름
	}
}
