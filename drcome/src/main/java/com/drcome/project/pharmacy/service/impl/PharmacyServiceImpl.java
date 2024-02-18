package com.drcome.project.pharmacy.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<Map<String, Object>> selectPrescriptionList(Map<String, Object> map, String date) {
		
		List<Map<String, Object>> listmap = null;
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String perdate = dateFormat.format(currentDate);
		map.put("date", date);
		
		if(date.equals(perdate)) {
			listmap = mapper.selectPrescriptionList(map);
		} 
		else {
			listmap = mapper.selectLastPerList( map);
		}
		return listmap;
	}
	
	/* 약 주성분 검색 */
	/*
	 * @Override public List<MedicineVO> findMedicine(String keyword) { return
	 * mapper.searchMedicine(keyword); }
	 */
	
	// 처방전조회
	@Override
	public List<PharmacySelectVO> getPerscription(int clinicNo) {
		return mapper.perscription(clinicNo);
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
	public int percount(Map<String, Object> map, String date) {
		
		Date currentDate = new Date(); System.out.println(currentDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String perdate = dateFormat.format(currentDate); System.out.println(perdate);
		
		int result = 0; 
		map.put("date", date);
		
		if(date.equals(perdate)) { 
			result = mapper.percount(map); 
		} else { 
			result = mapper.perLastcount(map); }
		
		return result;
	}

	@Override
	public Map<String, Object> updateproduce(PharmacySelectVO pharmacyselectVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;

		int result = mapper.updateProduceStatus(pharmacyselectVO);
		if (result == 1) {
			isSuccessed = true;
		}

		map.put("result", isSuccessed);
		map.put("target", pharmacyselectVO);
		
		return map;
	}

	@Override
	public int printStatusModify(PharmacySelectVO vo) {
		return mapper.printupdate(vo);
	}

	@Override
	public int printpharmacyModify(PharmacySelectVO vo) {
		return mapper.insertprintpharmacy(vo);
	}

	@Override
	public List<Map<String, Object>> perCurrList(Map<String, Object> map, String date) {
		List<Map<String, Object>> clistmap = null;
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String perdate = dateFormat.format(currentDate);
		map.put("date", date);
		
		if(date.equals(perdate)) {
			clistmap = mapper.currPerList(map);
		}
		 
		return clistmap;
	}

	@Override
	public int currpercount(Map<String, Object> map, String date) {
		Date currentDate = new Date(); System.out.println(currentDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String perdate = dateFormat.format(currentDate); System.out.println(perdate);
		
		int result = 0; 
		map.put("date", date);
		
		if(date.equals(perdate)) { 
			result = mapper.percountcurr(map); 
		}
		
		return result;
	}

}
