package com.drcome.project.doctor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.drcome.project.doctor.service.PatientVO;

public interface PatientMapper {

	//기본정보조회 
	public PatientVO selectPatientInfo(PatientVO vo);
	
	//내원이력조회
	public List<PatientVO> clinicList(@Param("hid") String hid, @Param("uid") String uid);
	
	//처방전가져오기
	public List<PatientVO> perscription(PatientVO vo);
	
	//약검색
	public List<PatientVO> mnameList(PatientVO vo);
	
}
