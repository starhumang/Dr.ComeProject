package com.drcome.project.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.drcome.project.admin.domain.Hospital;

public interface HospitalListRepository extends JpaRepository<Hospital, String>{
	public Page<Hospital> findByhospitalStatus(String hstatus, Pageable pageable);
}

