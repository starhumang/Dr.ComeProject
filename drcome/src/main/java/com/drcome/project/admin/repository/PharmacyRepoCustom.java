package com.drcome.project.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.drcome.project.admin.domain.Pharmacy;

public interface PharmacyRepoCustom {
	
	public Page<Pharmacy> findByhospitalStatus(String pstatus, Pageable pageable);
}
