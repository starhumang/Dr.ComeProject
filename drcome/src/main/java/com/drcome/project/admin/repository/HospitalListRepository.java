package com.drcome.project.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drcome.project.admin.domain.Hospital;

public interface HospitalListRepository extends JpaRepository<Hospital, String>{
	
}

