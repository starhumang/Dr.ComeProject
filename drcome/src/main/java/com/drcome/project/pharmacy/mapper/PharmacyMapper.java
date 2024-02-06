package com.drcome.project.pharmacy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.drcome.project.pharmacy.MedicineVO;
import com.drcome.project.pharmacy.PharmacySelectVO;
import com.drcome.project.pharmacy.PharmacyVO;

public interface PharmacyMapper {

	/* 단건 조회 */
	public PharmacyVO selectPharmacyInfo(PharmacyVO pharmacyVO);
	
	/* 약국별 처방 현황 */
	public List<Map<String, Object>> selectPrescriptionList(@Param("page") int page, @Param("date") String date, @Param("pharmacyId") String pharmacyId);
	
	/* 약국별 지난 처방 내역 */
	public List<Map<String, Object>> selectLastPerList(@Param("page") int page, @Param("date") String date, @Param("pharmacyId") String pharmacyId);
	
	/* 약 주성분 검색 */
	public List<MedicineVO> searchMedicine(String keyword);
	
	/*처방전 미리보기*/
	public List<PharmacySelectVO> perscription(PharmacySelectVO vo);
	
	/*처방전 미리보기*/
	public int updaterejection(PharmacySelectVO vo);
	
	//-----Total Count-----
	
	/* 약국별 처방 현황 페이징 */
	public int percount(Map<String, Object> parameters);
	
	/* 약국별 처방 내역 페이징 */
	public int perLastcount(Map<String, Object> parameters);
}
