package com.drcome.project.medical.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.medical.mapper.HospitalMapper;
import com.drcome.project.medical.repository.HospitalRepository;
import com.drcome.project.medical.service.DoctorVO;
import com.drcome.project.medical.service.HospitalService;

@Service
public class HospitalServiceImpl implements HospitalService{

	@Autowired HospitalRepository hrepo;
	@Autowired HospitalMapper hospitalMapper;

	//병원 단건조회(id로)
	@Override
	public Hospital findByhospitalId(String hospitalId) {
		Hospital hosSel = hrepo.findById(hospitalId).get();
		return hosSel;
	}

	//병원 - 의사 조회
	@Override
	public List<DoctorVO> getDoctorAll(String hospitalId) {
		return hospitalMapper.selectDrList(hospitalId);
	}

	//환자 조회
	@Override
	public List<Map<String, Object>> getPaientList(String hospitalId) {
		List<Map<String, Object>> listPa = hospitalMapper.selectPatientList(hospitalId);
		return listPa;
	}
	
	//환자 상세 조회
	@Override
	public List<Map<String, Object>> getPaientDetailList(String hospitalId, Integer patientNo) {
		List<Map<String, Object>> listPaDe = hospitalMapper.selectPatientDetailList(hospitalId, patientNo);
		return listPaDe;
	}
	
	
	
	
}
