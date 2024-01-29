package com.drcome.project.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {
	@GetMapping(value = {"/", "/home"})
	public String home() {
		return "/user/home";
	}

}
