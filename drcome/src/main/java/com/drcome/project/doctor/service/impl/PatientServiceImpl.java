package com.drcome.project.doctor.service.impl;

import java.util.List;

import javax.transaction.Transactional;

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
	public List<PatientVO> getClinicList(int page, PatientVO vo) {
		
		return mapper.clinicList(page, vo);
	}

	
	//진료기록 totalList
	@Override
	public int totalList(PatientVO vo) {
		return mapper.cntList(vo);
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

	// 진료기록 인서트
	@Override
	@Transactional
	public int insertClinic(PatientVO vo) {

		int result = 0;

		List<PatientVO> plist = vo.getPerary();

		// 초진이면 //환자테이블 인서트
		if (vo.getVisit().equals("first")) {
			mapper.patientInsert(vo);
		}
		// 재진이면
		// 최근 진료 날짜 업데이트
		mapper.updateDate(vo);
		// 환자번호 select
		int pno = mapper.searchPno(vo);
		// vo에 set
		vo.setPatientNo(pno);
		// 진료기록 insert
		result = mapper.insertClinic(vo);
		// 부여된 cno가져와서
		int cno = vo.getClinicNo();
		// 처방전이있다면
		if (vo.getPerscriptionYn() == null) {
			for (PatientVO obj : plist) {
				obj.setClinicNo(cno);
				// System.out.println("야약약약" + obj);
				result = mapper.insertPer(obj);
			}
		}

		//System.out.println(result);
		return result;
	}


	

}
