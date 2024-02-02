package com.drcome.project.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drcome.project.admin.domain.Usertable;

public interface UsertableRepository extends JpaRepository<Usertable, String>{
	
}

