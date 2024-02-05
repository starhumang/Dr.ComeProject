package com.drcome.project.main.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

@Service
public interface MainService {
	
	//병원목록
	public List<HospitalVO> getHosList();
	
	//약국목록
	public List<PharmacyVO> getPhaList(int num);
	
	//병원상세정보
	public HospitalVO getHos(String hospitalId);
	
	//약국상세정보
	public PharmacyVO getPha(String pharmacyId);
	
	//병원검색
	public List<HospitalVO> searchHosList(String word);
	
	//약국검색
	public List<PharmacyVO> searchPhaList(String word);

	//병원진료과목 검색
	public List<HospitalVO> searchSubjectHos(String mainSubject);
		
	//해당약국 약 처방 신청기록 찾기
	public Map<String, Object> checkPrescription(int clinicNo, String pharmacyId);
}
