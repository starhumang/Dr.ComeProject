package com.drcome.project.detail.mapper;

import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

public interface DetailMapper {
	
	//병원상세
	public HospitalVO selectHos(String hospitalId);
	
	//약국상세
	public PharmacyVO selectPha(String pharmacyId);
}
