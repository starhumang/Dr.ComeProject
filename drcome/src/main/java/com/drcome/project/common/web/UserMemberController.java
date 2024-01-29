package com.drcome.project.common.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.drcome.project.common.mapper.UserMemberMapper;

@Controller
public class UserMemberController {
	
	@Autowired
	UserMemberMapper dao;
	
	@GetMapping(value = {"/", "/home"})
	public String home() {
		return "/user/home";
	}
	
	@GetMapping("/join")
	public String join() {
		return "member/userjoin";
	}
	
	@GetMapping("/userlist")
	public String userList(Model model) {
		model.addAttribute("users", dao.selectMemberList(null));
		return "member/userlist";
	}

}
