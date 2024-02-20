package com.drcome.project.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.drcome.project.admin.domain.Hospital;

public interface HospitalListRepoCustom {

	public Page<Hospital> findByhospitalStatus(String hstatus, Pageable pageable);
}
