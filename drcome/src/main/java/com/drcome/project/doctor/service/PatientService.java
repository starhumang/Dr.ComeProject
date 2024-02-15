package com.drcome.project.doctor.service;

import java.util.List;

public interface PatientService {

	// 환자 기본정보 조회
	public PatientVO getPatientInfo(PatientVO vo);

	// 환자 진료내역 조회
	public List<PatientVO> getClinicList(int page, PatientVO vo);

	// 진료 리스트 total
	public int totalList(PatientVO vo);

	// 환자 처방전 조회
	public List<PatientVO> getPerscription(PatientVO vo);

	// 약검색
	public List<PatientVO> getmnameList(PatientVO vo);

	// 진료기록 insert
	public int insertClinic(PatientVO vo);

	// 예약상태 진료완료로 업데이트
	public int modifyReserve(PatientVO vo);

}
