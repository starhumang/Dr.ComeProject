package com.drcome.project.medical.mapper;

import java.util.List;
import java.util.Map;

import com.drcome.project.medical.service.DoctorVO;

public interface HospitalMapper {
	
	//병원-의사 조회
	public List<DoctorVO> selectDrList(String hospitalId);
	
	//환자 조회
	public List<Map<String, Object>> selectPatientList(String hospitalId);
	
	//환자 진료 상세 조회
	public List<Map<String, Object>> selectPatientDetailList(String hospitalId, Integer patientNo);
}
