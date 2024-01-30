package com.drcome.project.pharmacy.mapper;

import com.drcome.project.pharmacy.PharmacySelectVO;
import com.drcome.project.pharmacy.PharmacyVO;

public interface PharmacyMapper {

	/* 단건 조회 */
	public PharmacyVO selectPharmacyInfo(PharmacyVO pharmacyVO);
	
	/* 약국별 처방 현황 */
	public PharmacySelectVO selectPrescriptionList(PharmacySelectVO pharmacyselectVO);
}
