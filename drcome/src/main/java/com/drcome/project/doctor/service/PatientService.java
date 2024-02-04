package com.drcome.project.doctor.service;

import java.util.List;

public interface PatientService {

	// 환자 기본정보 조회
	public PatientVO getPatientInfo(PatientVO vo);

	// 환자 진료내역 조회
	public List<PatientVO> getClinicList(String hid, String uid);

	// 환자 처방전 조회
	public List<PatientVO> getPerscription(PatientVO vo);

	// 약검색
	public List<PatientVO> getmnameList(PatientVO vo);

	// 재진-환자번호조회
	public int getPno(PatientVO vo);

	// 진료기록 insert
	public int insertClinic(PatientVO vo);

}
