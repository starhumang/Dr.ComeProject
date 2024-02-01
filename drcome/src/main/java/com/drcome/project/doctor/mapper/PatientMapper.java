package com.drcome.project.doctor.mapper;

import java.util.List;

import com.drcome.project.doctor.service.PatientVO;

public interface PatientMapper {

	//기본정보조회 
	public PatientVO selectPatientInfo(PatientVO vo);
	
	//내원이력조회
	public List<PatientVO> clinicList();
	
	//처방전가져오기
	public List<PatientVO> perscription(PatientVO vo);
	
}
