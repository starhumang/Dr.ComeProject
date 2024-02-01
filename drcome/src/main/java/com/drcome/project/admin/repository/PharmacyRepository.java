package com.drcome.project.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drcome.project.admin.domain.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, String>{
	
}

