package com.drcome.project.pharmacy.service;

import java.util.List;
import java.util.Map;

import com.drcome.project.pharmacy.MedicineVO;
import com.drcome.project.pharmacy.PharmacySelectVO;
import com.drcome.project.pharmacy.PharmacyVO;

public interface PharmacyService {

	/* 단건 조회 */
	public PharmacyVO selectPharmacyInfo(PharmacyVO pharmacyVO);

	/* 약국별 처방 현황 */
	public List<Map<String, Object>> selectPrescriptionList(String date, String pharmacyId);

	/* 약 주성분 검색 */
	public List<MedicineVO> findMedicine(String keyword);

	/* 환자 처방전 조회 */
	public List<PharmacySelectVO> getPerscription(PharmacySelectVO vo);

	/* 처방전 반환 */
	public Map<String, Object> updaterejection(PharmacySelectVO pharmacyselectVO);

	/*
	 * 처방현황 페이징 public List<Map<String, Object>> getperPage(String date, String
	 * pharmacyId, int pageNo, int pageSize);
	 * 
	 * 처방내역 페이징 public List<Map<String, Object>> getperLastPage(int pageNo, int
	 * pageSize);
	 */
}
