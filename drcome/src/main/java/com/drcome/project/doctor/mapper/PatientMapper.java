package com.drcome.project.doctor.mapper;

import com.drcome.project.doctor.service.PatientVO;

public interface PatientMapper {

	//기본정보조회 
	public PatientVO selectPatientInfo(PatientVO vo);
	
}
