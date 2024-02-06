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
import com.drcome.project.medical.service.NoticeVO;

@Service
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	HospitalRepository hrepo;
	@Autowired
	HospitalMapper hospitalMapper;

	/* 대시보드 */
	// 오늘의 진료 내역 리스트
	@Override
	public List<Map<String, Object>> getTodayReserve(String hospitalId) {
		List<Map<String, Object>> listToday = hospitalMapper.selectTodayReserve(hospitalId);
		return listToday;
	}

	// QnA답변O

	@Override
	public List<Map<String, Object>> getQnAO(String hospitalId) {
		List<Map<String, Object>> listQnAO = hospitalMapper.selectQnAO(hospitalId);
		return listQnAO;
	}

	// QnA답변X
	@Override
	public List<Map<String, Object>> getQnAX(String hospitalId) {
		List<Map<String, Object>> listQnAX = hospitalMapper.selectQnAX(hospitalId);
		return listQnAX;
	}

	/* 예약내역 - clinic */
	//Main
	@Override
	public List<Map<String, Object>> getRerveList(String hospitalId) {
		List<Map<String, Object>> listReserveAll = hospitalMapper.selectReserveMain(hospitalId);
		return listReserveAll;
	}

	//Dr
	@Override
	public List<Map<String, Object>> getReserveDrList(String hospitalId, Integer doctorNo) {
		List<Map<String, Object>> listReserveDr = hospitalMapper.selectReserveDr(hospitalId, doctorNo);
		return listReserveDr;
	}
	
	/* QnA */
	// QnA 전체
	@Override
	public List<Map<String, Object>> getQnaList(String hospitalId) {
		List<Map<String, Object>> listQnaAll = hospitalMapper.selectQnaList(hospitalId);
		return listQnaAll;
	}

	// QnA 단건상세
	@Override
	public List<Map<String, Object>> getQnaInfo(String hospitalId, Integer qnaNo) {
		List<Map<String, Object>> qnaInfo = hospitalMapper.selectQnaInfo(hospitalId, qnaNo);
		return qnaInfo;
	}

	/* 공지사항 */
	// 공지사항 전체
	@Override
	public List<Map<String, Object>> getNoticeList(String hospitalId) {
		List<Map<String, Object>> listNoticeAll = hospitalMapper.selectNoticeList(hospitalId);
		return listNoticeAll;
	}

	// 공지사항 단건상세
	@Override
	public List<NoticeVO> getNoticeDetail(String hospitalId, Integer noticeNo) {
		List<NoticeVO> noticeInfo = hospitalMapper.selectNoList(hospitalId, noticeNo);
		return noticeInfo;
	}

	// 공지사항 등록 + 첨부파일
	@Override
	public int insertNoticeInfo(NoticeVO vo) {
		return hospitalMapper.insertNotice(vo);
	}
	@Override
	public int insertAttach(NoticeVO vo) {
		return hospitalMapper.insertAttach(vo);
	}

	/* 병원프로필 */
	// 병원 단건조회(id로)
	@Override
	public Hospital findByhospitalId(String hospitalId) {
		Hospital hosSel = hrepo.findById(hospitalId).get();
		return hosSel;
	}

	// 병원 - 의사 조회
	@Override
	public List<DoctorVO> getDoctorAll(String hospitalId) {
		return hospitalMapper.selectDrList(hospitalId);
	}

	/* 환자리스트 */
	// 환자 조회
	@Override
	public List<Map<String, Object>> getPaientList(String hospitalId) {
		List<Map<String, Object>> listPa = hospitalMapper.selectPatientList(hospitalId);
		return listPa;
	}

	// 환자 상세 조회
	@Override
	public List<Map<String, Object>> getPaientDetailList(String hospitalId, Integer patientNo) {
		List<Map<String, Object>> listPaDe = hospitalMapper.selectPatientDetailList(hospitalId, patientNo);
		return listPaDe;
	}



}
