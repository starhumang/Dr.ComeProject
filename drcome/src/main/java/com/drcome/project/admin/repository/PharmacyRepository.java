package com.drcome.project.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.admin.domain.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, String>{

	public List<Pharmacy> findBypharmacyStatus(String pharmacyStatus);
}

