package com.drcome.project.medical.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drcome.project.medical.domain.Hospital;
import com.drcome.project.medical.repository.HospitalRepository;

@RestController
public class HospitalController {
	@Autowired
	HospitalRepository repo;
	
//	@GetMapping(value = {"/hospitalHome"})
//	public String home() {
//		return "/hospital/home";
//	}
	
	@GetMapping("/hospitalHome")
	public Iterable<Hospital> hospital() {
		return repo.findAll();
	}
}
