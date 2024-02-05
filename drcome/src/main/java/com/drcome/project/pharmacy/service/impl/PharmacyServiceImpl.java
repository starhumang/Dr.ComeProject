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
	public List<Map<String, Object>> selectPrescriptionList(String date, String pharmacyId) {
		
		List<Map<String, Object>> listmap = null;
		Date currentDate = new Date();
		System.out.println(currentDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String perdate = dateFormat.format(currentDate);
        System.out.println(perdate);
		
		if(date.equals(perdate)) {
			listmap = mapper.selectPrescriptionList(date, pharmacyId);
		} 
		else {
			listmap = mapper.selectLastPerList(date, pharmacyId);
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

}
