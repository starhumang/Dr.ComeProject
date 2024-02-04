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
	
	/* 대시보드 */
	//오늘의 진료 내역 리스트
	@Override
	public List<Map<String, Object>> getTodayReserve(String hospitalId) {
		List<Map<String, Object>> listToday = hospitalMapper.selectTodayReserve(hospitalId);
		return listToday;
	}
	
	//QnA답변O
	
	@Override
	public List<Map<String, Object>> getQnAO(String hospitalId) {
		List<Map<String, Object>> listQnAO = hospitalMapper.selectQnAO(hospitalId);
		return listQnAO;
	}

	//QnA답변X
	@Override
	public List<Map<String, Object>> getQnAX(String hospitalId) {
		List<Map<String, Object>> listQnAX = hospitalMapper.selectQnAX(hospitalId);
		return listQnAX;
	}
	
	/* QnA */
	//QnA 전체
	@Override
	public List<Map<String, Object>> getQnaList(String hospitalId) {
		List<Map<String, Object>> listQnaAll = hospitalMapper.selectQnaList(hospitalId);
		return listQnaAll;
	}
	
	/* 병원프로필 */
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

	/* 환자리스트 */
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
