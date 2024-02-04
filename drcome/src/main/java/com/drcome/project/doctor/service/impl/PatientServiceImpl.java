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
		System.out.println("처방전" + mapper.perscription(vo));
		return mapper.perscription(vo);
	}

	// 약검색
	@Override
	public List<PatientVO> getmnameList(PatientVO vo) {
		return mapper.mnameList(vo);
	}

	// 재진-환자번호조회
	@Override
	public int getPno(PatientVO vo) {
		// System.out.println("서비스" + mapper.searchPno(vo));
		return mapper.searchPno(vo);
	}

	// 진료기록 인서트 후 생성된 clinicNo 가져오기
	@Override
	public int insertClinic(PatientVO vo) {
		mapper.insertClinic(vo);
		return vo.getClinicNo();
	}

	// 처방전 인서트
	@Override
	public int insertPer(PatientVO vo) {
		return mapper.insertPer(vo);
	}

	// 신규환자등록
	@Override
	public int patientInsert(PatientVO vo) {
		return mapper.patientInsert(vo);
	}

}
