package com.drcome.project.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public Page<Usertable> getuserAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return urepo.findAll(pageable);
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

	@Override
	public Page<Hospital> findByhospitalStatus(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return hrepo.findAll(pageable);
	}

	@Override
	public Page<Pharmacy> findBypharmacyStatus(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return prepo.findAll(pageable);
	}
}