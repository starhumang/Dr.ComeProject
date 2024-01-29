package com.drcome.project.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drcome.project.admin.domain.User;
import com.drcome.project.admin.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository repo;
	
	@GetMapping("/admin/user")
	//@ResponseBody //restController일 땐 안 써도 됨
	public Iterable<User> user() {
		return repo.findAll();
	}
}
