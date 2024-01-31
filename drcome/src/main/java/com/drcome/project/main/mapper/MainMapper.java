package com.drcome.project.main.mapper;

import java.util.List;

import com.drcome.project.medical.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

public interface MainMapper {
	
	//병원리스트
	public List<HospitalVO> selectHosList();
	
	//약국리스트
	public List<PharmacyVO> selectPhaList();
}
