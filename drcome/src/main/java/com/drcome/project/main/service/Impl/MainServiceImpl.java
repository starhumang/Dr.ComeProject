package com.drcome.project.main.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.main.mapper.MainMapper;
import com.drcome.project.main.service.MainService;
//import com.drcome.project.main.service.PharmacyVO;
import com.drcome.project.medical.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

@Service
public class MainServiceImpl implements MainService {
	
	@Autowired
	MainMapper mainMapper;

	@Override
	public List<HospitalVO> getHosList() {
		return mainMapper.selectHosList();
	}

	@Override
	public List<PharmacyVO> getPhaList() {
		return mainMapper.selectPhaList();
	}

}
