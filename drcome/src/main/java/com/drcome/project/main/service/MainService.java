package com.drcome.project.main.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.drcome.project.medical.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

@Service
public interface MainService {
	
	//병원목록
	public List<HospitalVO> getHosList();
	
	//약국목록
	public List<PharmacyVO> getPhaList();
}
