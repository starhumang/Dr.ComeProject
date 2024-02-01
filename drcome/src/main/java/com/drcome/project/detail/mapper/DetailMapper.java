package com.drcome.project.detail.mapper;

import com.drcome.project.medical.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

public interface DetailMapper {
	
	//병원상세
	public HospitalVO selectHos(HospitalVO hospitalVO);
	
	//약국상세
	public PharmacyVO selectPha(PharmacyVO pharmacyVO);
	
}
