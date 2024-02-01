package com.drcome.project.doctor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.doctor.mapper.PatientMapper;
import com.drcome.project.doctor.service.PatientService;
import com.drcome.project.doctor.service.PatientVO;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	PatientMapper mapper;

	// 환자 기본정보 조회
	@Override
	public PatientVO getPatientInfo(PatientVO vo) {
		return mapper.selectPatientInfo(vo);
	}

	// 환자 진료기록 조회
	@Override
	public List<PatientVO> getClinicList(String hid, String uid) {
		return mapper.clinicList(hid, uid);
	}

	// 처방전조회
	@Override
	public List<PatientVO> getPerscription(PatientVO vo) {
		return mapper.perscription(vo);
	}

	// 약검색
	@Override
	public List<PatientVO> getmnameList(PatientVO vo) {
		return null;
	}

}
