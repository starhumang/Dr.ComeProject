package com.drcome.project.medical.service;

import java.util.List;
import java.util.Map;

import com.drcome.project.admin.domain.Hospital;

public interface HospitalService {
	
	//병원 단건조회(id로)
	public Hospital findByhospitalId(String hospitalId);
	
	//병원-의사 조회
	public List<DoctorVO> getDoctorAll(String hospitalId);
	
	//환자 조회
	public List<Map<String, Object>> getPaientList(String hospitalId);
	
	//환자 상세 진료내역 조회
	public List<Map<String, Object>> getPaientDetailList(String hospitalId, Integer patientNo);
}
