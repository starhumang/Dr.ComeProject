package com.drcome.project.medical.mapper;

import java.util.List;

import com.drcome.project.medical.DoctorVO;

public interface HospitalMapper {
	
	//병원-의사 조회
	public List<DoctorVO> selectDrList(String hospitalId);
}
