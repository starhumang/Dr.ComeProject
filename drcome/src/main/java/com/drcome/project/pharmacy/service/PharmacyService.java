package com.drcome.project.pharmacy.service;

import java.util.List;
import java.util.Map;

import com.drcome.project.pharmacy.PharmacySelectVO;
import com.drcome.project.pharmacy.PharmacyVO;

public interface PharmacyService {

	/* 단건 조회 */
	public PharmacyVO selectPharmacyInfo(PharmacyVO pharmacyVO);

	/* 약국별 처방 현황 */
	public List<Map<String, Object>> selectPrescriptionList(Map<String, Object> map, String date);
	
	/* 약국별 처방 내역 현재 날짜 */
	public List<Map<String, Object>> perCurrList(Map<String, Object> map, String date);
	
	/* 약국별 처방 내역 */
//	public List<Map<String, Object>> selectLastPerList(int page, String date, String pharmacyId);
	
	public int percount(Map<String, Object> map, String date);
	
	public int currpercount(Map<String, Object> map, String date);

	/* 약 주성분 검색 */
	//public List<MedicineVO> findMedicine(String keyword);

	/* 환자 처방전 조회 */
	public List<PharmacySelectVO> getPerscription(int clinicNo);

	/* 처방전 반환 */
	public Map<String, Object> updaterejection(PharmacySelectVO pharmacyselectVO);
	
	/* 처방전 출력시 update */
	public int printStatusModify(PharmacySelectVO vo);
	
	/* 처방전 출력시 출력 약국 insert */
	public int printpharmacyModify(PharmacySelectVO vo);
}
