package com.drcome.project.pharmacy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PharmacyController {

	@GetMapping("/pharmacy")
	public String home() {
		return "pharmacy/home";
	}
}
