package com.drcome.project.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.drcome.project.admin.domain.Usertable;

public interface UsertableRepository extends JpaRepository<Usertable, String> {
	
	public Page<Usertable> findByuserStatus(String ustatus, Pageable pageable);
}