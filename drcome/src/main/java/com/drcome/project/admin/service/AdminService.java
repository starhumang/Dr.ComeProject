package com.drcome.project.admin.service;

import org.springframework.data.domain.Page;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.admin.domain.Pharmacy;
import com.drcome.project.admin.domain.Usertable;

public interface AdminService {

	public Page<Usertable> getuserAll(int pageNo, int pageSize);
	public Page<Hospital> findByhospitalStatus(int pageNo, int pageSize);
	public Page<Pharmacy> findBypharmacyStatus(int pageNo, int pageSize);
	public Hospital updateStatus(String hospitalId);
	public Pharmacy updatePharmacyStatus(String PharmacyId);
	/* public Page<Usertable> getEntitiesWithPaging(int pageNo, int pageSize); */
}
