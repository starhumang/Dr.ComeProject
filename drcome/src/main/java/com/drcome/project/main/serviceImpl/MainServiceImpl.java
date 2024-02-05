package com.drcome.project.main.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.main.mapper.MainMapper;
import com.drcome.project.main.service.MainService;
import com.drcome.project.medical.service.HospitalVO;
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
	public List<PharmacyVO> getPhaList(int num) {
		return mainMapper.selectPhaList(num);
	}
	
	@Override
	public HospitalVO getHos(String hospitalId) {
		return mainMapper.selectHos(hospitalId);
	}

	@Override
	public PharmacyVO getPha(String pharmacyId) {
		return mainMapper.selectPha(pharmacyId);
	}

	@Override
	public List<HospitalVO> searchHosList(String word) {
		return mainMapper.searchHosList(word);
	}

	@Override
	public List<PharmacyVO> searchPhaList(String word) {
		return mainMapper.searchPhaList(word);
	}

	@Override
	public List<HospitalVO> searchSubjectHos(String mainSubject) {
		return mainMapper.searchSubjectHos(mainSubject);
	}

	@Override
	public Map<String, Object> checkPrescription(int clinicNo, String pharmacyId) {
		Map<String, Object>map = new HashMap<>();
		boolean success = false;
		int result = mainMapper.checkPrescription(clinicNo, pharmacyId);
		if(result == 1) {
			success = true;
		}
		map.put("result",success );
		return map;
	}



}
