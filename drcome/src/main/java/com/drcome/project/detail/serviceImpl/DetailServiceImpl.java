package com.drcome.project.detail.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.detail.mapper.DetailMapper;
import com.drcome.project.detail.service.DetailService;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

@Service
public class DetailServiceImpl implements DetailService {
	
	@Autowired
	DetailMapper detailMapper;
	
	
	@Override
	public HospitalVO getHos(String hospitalId) {
		return detailMapper.selectHos(hospitalId);
	}

	@Override
	public PharmacyVO getPha(String pharmacyId) {
		return detailMapper.selectPha(pharmacyId);
	}

}
