package com.drcome.project.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.admin.domain.Pharmacy;
import com.drcome.project.admin.domain.Usertable;
import com.drcome.project.admin.repository.HospitalListRepository;
import com.drcome.project.admin.repository.PharmacyRepository;
import com.drcome.project.admin.repository.UsertableRepository;
import com.drcome.project.admin.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	UsertableRepository urepo;
	
	@Autowired
	HospitalListRepository hrepo;
	
	@Autowired
	PharmacyRepository prepo;
	
	@Override
	public List<Usertable> getuserAll() {
		return urepo.findAll();
	}

	@Override
	public Hospital updateStatus(String HospitalId) {
		Hospital hospitalsearch= hrepo.findById(HospitalId).get();
		hospitalsearch.hospitalStatusUdpate(); 
		hrepo.save(hospitalsearch);
		return hospitalsearch;
	}

	@Override
	public Pharmacy updatePharmacyStatus(String PharmacyId) {
		Pharmacy pharmacysearch= prepo.findById(PharmacyId).get();
		pharmacysearch.pharmacyStatusUdpate(); 
		prepo.save(pharmacysearch);
		return pharmacysearch;
	}
	
	

}
