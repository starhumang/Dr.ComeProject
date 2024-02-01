package com.drcome.project.detail.service;

import org.springframework.stereotype.Service;

import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

@Service
public interface DetailService {
	
	//병원상세
	public HospitalVO getHos(String hospitalId);
	
	//약국상세
	public PharmacyVO getPha(String pharmacyId);
}
