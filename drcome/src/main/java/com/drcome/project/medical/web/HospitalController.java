package com.drcome.project.medical.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.drcome.project.medical.domain.Hospital;
import com.drcome.project.medical.repository.HospitalRepository;

@Controller
public class HospitalController {
	@Autowired
	HospitalRepository repo;
	
	@GetMapping(value = {"/hospital", "/hospitalHome"})
	public String home() {
		return "/hospital/home";
	}
	
	@GetMapping("/hospital/myProfile")
	public String hospital(Model model) {
		Iterable<Hospital> hosList = repo.findAll();
		model.addAttribute("hospitalList", hosList);
		return "hospital/myprofie";
	}
}
