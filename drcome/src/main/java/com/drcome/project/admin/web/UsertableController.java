package com.drcome.project.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.drcome.project.admin.domain.Usertable;
import com.drcome.project.admin.repository.UsertableRepository;

@Controller
public class UsertableController {

	@Autowired
	UsertableRepository repo;
	
//	@GetMapping("/admin")
//	public String home() {
//		return "admin/home";
//	}
	
	@GetMapping("/admin/user")
	//@ResponseBody //restController일 땐 안 써도 됨
	public String user(Model model) {
		Iterable<Usertable> userlist = repo.findAll();
		model.addAttribute("list", userlist);
		return "admin/adminUser";
	}
}
