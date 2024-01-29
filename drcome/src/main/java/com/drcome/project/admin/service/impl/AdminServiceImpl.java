package com.drcome.project.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.drcome.project.admin.domain.Usertable;
import com.drcome.project.admin.repository.UsertableRepository;
import com.drcome.project.admin.service.AdminService;

public class AdminServiceImpl implements AdminService {

	@Autowired
	UsertableRepository repo;
	
	@Override
	public List<Usertable> getuserAll() {
		return repo.findAll();
	}

}
