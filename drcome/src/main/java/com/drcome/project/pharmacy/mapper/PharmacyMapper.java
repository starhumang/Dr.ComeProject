package com.drcome.project.pharmacy.mapper;

import java.util.List;
import java.util.Map;

import com.drcome.project.pharmacy.PharmacySelectVO;
import com.drcome.project.pharmacy.PharmacyVO;

public interface PharmacyMapper {

	/* 단건 조회 */
	public PharmacyVO selectPharmacyInfo(PharmacyVO pharmacyVO);
	
	/* 약국별 처방 현황 */
	public List<Map<String, Object>> selectPrescriptionList(Map<String, Object> map);
	
	/* 약국별 현재 날짜 처방 내역 */
	public List<Map<String, Object>> currPerList(Map<String, Object> map);
	
	/* 약국별 지난 처방 내역 */
	public List<Map<String, Object>> selectLastPerList(Map<String, Object> map);
	
	/* 약 주성분 검색 */
	//public List<MedicineVO> searchMedicine(String keyword);
	
	/*처방전 미리보기*/
	public List<PharmacySelectVO> perscription(int clinicNo);
	
	/*처방전 미리보기*/
	public int updaterejection(PharmacySelectVO vo);
	
	/* 반환 시, 상태 업데이트 */
	public int updateProduceStatus(PharmacySelectVO vo);
	
	//-----Total Count-----
	
	/* 약국별 처방 현황 페이징 */
	public int percount(Map<String, Object> map);
	
	/* 약국별 현재 날짜 처방 내역 페이징 */
	public int percountcurr(Map<String, Object> map);
	
	/* 약국별 처방 내역 페이징 */
	public int perLastcount(Map<String, Object> map);
	
	// ---- Print Update ----
	
	/* 처방전 출력시 update */
	public int printupdate(PharmacySelectVO vo);
	
	/* 처방전 출력시 출력 약국 insert */
	public int insertprintpharmacy(PharmacySelectVO vo);
}
