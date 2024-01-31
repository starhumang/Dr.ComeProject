package com.drcome.project.pharmacy.service;

import java.util.List;
import java.util.Map;

import com.drcome.project.pharmacy.PharmacyVO;

public interface PharmacyService {

	/* 단건 조회 */
	public PharmacyVO selectPharmacyInfo(PharmacyVO pharmacyVO);
	
	/* 약국별 처방 현황 */
	public List<Map<String, Object>> selectPrescriptionList();
}
