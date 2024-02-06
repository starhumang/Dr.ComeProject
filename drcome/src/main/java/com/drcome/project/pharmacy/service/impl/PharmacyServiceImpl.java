package com.drcome.project.pharmacy.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.pharmacy.MedicineVO;
import com.drcome.project.pharmacy.PharmacySelectVO;
import com.drcome.project.pharmacy.PharmacyVO;
import com.drcome.project.pharmacy.mapper.PharmacyMapper;
import com.drcome.project.pharmacy.service.PharmacyService;

@Service
public class PharmacyServiceImpl implements PharmacyService{

	@Autowired
	PharmacyMapper mapper;
	
	/* 약국 상세 조회 */
	@Override
	public PharmacyVO selectPharmacyInfo(PharmacyVO pharmacyVO) {
		return mapper.selectPharmacyInfo(pharmacyVO);
	}
	
	/* 약국별 처방 현황(현재 날짜) */
	@Override
	public List<Map<String, Object>> selectPrescriptionList(int page, String date, String pharmacyId) {
		
		List<Map<String, Object>> listmap = null;
		Date currentDate = new Date();
		System.out.println(currentDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String perdate = dateFormat.format(currentDate);
        System.out.println(perdate);
		
		if(date.equals(perdate)) {
			listmap = mapper.selectPrescriptionList(page, date, pharmacyId);
		} 
		else {
			listmap = mapper.selectLastPerList(page, date, pharmacyId);
		}
		return listmap;
	}
	
	/* 약 주성분 검색 */
	@Override
	public List<MedicineVO> findMedicine(String keyword) {
		return mapper.searchMedicine(keyword);
	}
	
	// 처방전조회
	@Override
	public List<PharmacySelectVO> getPerscription(PharmacySelectVO vo) {
		return mapper.perscription(vo);
	}
	
	/* 처방전 반환 시 업데이트 */
	@Override
	public Map<String, Object> updaterejection(PharmacySelectVO pharmacyselectVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;

		int result = mapper.updaterejection(pharmacyselectVO);
		if (result == 1) {
			isSuccessed = true;
		}

		map.put("result", isSuccessed);
		map.put("target", pharmacyselectVO);
		
		return map;
	}

	@Override
	public int percount(Map<String, Object> parameters, String date) {
		
		Date currentDate = new Date();
		System.out.println(currentDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String perdate = dateFormat.format(currentDate);
        System.out.println(perdate);
		
        int result = 0;
		if(date.equals(perdate)) {
			result = mapper.percount(parameters);
		} 
		else {
			result = mapper.perLastcount(parameters);
		}
		
		return result;
	}

	/*
	 * @Override public List<Map<String, Object>> getperPage(int pageNo, int
	 * pageSize) { // 페이지 번호와 페이지 크기를 이용하여 offset을 계산 int offset = (pageNo - 1) *
	 * pageSize; int limit = pageSize;
	 * 
	 * // 전체 레코드 수 조회 int totalCount = mapper.percount();
	 * 
	 * // 페이징된 결과 조회 List<Map<String, Object>> pagedData =
	 * mapper.perListpage(offset, limit);
	 * 
	 * // 페이징된 결과와 전체 레코드 수를 반환합니다. return pagedData; }
	 * 
	 * @Override public List<Map<String, Object>> getperLastPage(int pageNo, int
	 * pageSize) { int offset = (pageNo - 1) * pageSize; int limit = pageSize;
	 * 
	 * int totalCountL = mapper.perLastcount();
	 * 
	 * List<Map<String, Object>> pagedDataL = mapper.perLastListpage(offset, limit);
	 * 
	 * return pagedDataL; }
	 */

}
