package com.drcome.project.doctor.service;

import java.util.List;

public interface PatientService {

	//환자 기본정보 조회 
	public PatientVO getPatientInfo(PatientVO vo);
	//환자 진료내역 조회
	public List<PatientVO> getClinicList();

}
