package com.drcome.project.medical.service;

import java.util.List;
import java.util.Map;

import com.drcome.project.admin.domain.Hospital;

public interface HospitalService {
	
	/* 대시보드 */
	//오늘의 진료현황 리스트
	public List<Map<String, Object>> getTodayReserve(String hospitalId);
	
	//QnA답변O
	public List<Map<String, Object>> getQnAO(String hospitalId);
	
	//QnA답변X
	public List<Map<String, Object>> getQnAX(String hospitalId);
	
	/* 환자리스트 */
	//환자 조회
	public List<Map<String, Object>> getPaientList(Map<String, Object> map);
	public int patientCount(Map<String, Object> map);
	
	//환자 상세 진료내역 조회
	public List<Map<String, Object>> getPaientDetailList(Map<String, Object> map);
	public int patientInfoCount(Map<String, Object> map);

	//환자 진료내역 단건 조회
	public Map<String, Object> getPaientClinicInfo(String hospitalId, Integer patientNo, Integer clinicNo);
	
	//환자 진료내역 단건 처방전 조회
	public List<Map<String, Object>> getpaientPillInfo(Integer clinicNo);
	
	/* 예약내역 - clinic */
	//Main
	public List<Map<String, Object>> getRerveList(String hospitalId, String date, String reserveKindstatus);
	
	//Dr
	public List<Map<String, Object>> getReserveDrList(String hospitalId, Integer doctorNo, String date, String reserveKindstatus);
	
	//Dr리스트
	public List<Map<String, Object>> getDrAllList(String hospitalId);
	
	/* QnA */
	//QnA 전체
	public List<Map<String, Object>> getQnaList(Map<String, Object> map);
	public int qnaCount(Map<String, Object> map);
	
	//QnA 단건상세
	public List<Map<String, Object>> getQnaInfo(String hospitalId, Integer qnaNo);
	
	/* 공지사항 */
	//공지사항 전체	
	public List<Map<String, Object>> getNoticeList(int page, int type, String keyword, String hospitalId);
	public int noticeCount(int type, String keyword, String hospitalId);
	
	//공지사항 단건상세
	public NoticeVO getNoticeDetail(NoticeVO noticeVO);
	
	//공지사항 등록
	public int insertNoticeInfo(NoticeVO vo);
	public int insertAttach(NoticeVO vo);
	
	//공지사항 수정
	public int updateNotice(NoticeVO vo);
	public int deleteAttachment(int noticeNo); 
	
	/* 병원프로필 */
	//병원 단건조회(id로)
	public Hospital findByhospitalId(String hospitalId);
	
	//병원-의사 조회
	public List<DoctorVO> getDoctorAll(String hospitalId);
	
	// 의사 번호 조회
	public int getCurrentDoctorNo();
	
	// 의사 정보 조회
	public DoctorVO selectDoctor(int doctorNo);
	
	// 의사 등록
	public int insertDoctor(DoctorVO vo);
	
	// 의사 수정
	public int updateDoctor(DoctorVO vo);
	
}
