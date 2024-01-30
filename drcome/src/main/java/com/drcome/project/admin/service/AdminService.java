package com.drcome.project.admin.service;

import java.util.List;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.admin.domain.Pharmacy;
import com.drcome.project.admin.domain.Usertable;

public interface AdminService {

	public List<Usertable> getuserAll();
	public Hospital updateStatus(String hospitalId);
	public Pharmacy updatePharmacyStatus(String PharmacyId);
}
