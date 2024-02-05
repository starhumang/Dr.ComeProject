package com.drcome.project.doctor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.drcome.project.doctor.service.PatientVO;

public interface PatientMapper {

	// 기본정보조회
	public PatientVO selectPatientInfo(PatientVO vo);

	// 진료기록 리스트 
	public List<PatientVO> clinicList(@Param("page") int page, @Param("id") PatientVO vo);
	
	// 진료 total 
	public int cntList (PatientVO vo);

	// 처방전가져오기
	public List<PatientVO> perscription(PatientVO vo);

	// 약검색
	public List<PatientVO> mnameList(PatientVO vo);

	// 재진 - 환자번호 받아오기
	public int searchPno(PatientVO vo);
	
	//재진 - 환자날짜 업데이트
	public int updateDate (PatientVO vo);

	// 진료기록 insert
	public int insertClinic(PatientVO vo);

	// 처방전 insert
	public int insertPer(PatientVO vo);

	// 신규환자 insert
	public int patientInsert(PatientVO vo);
}
