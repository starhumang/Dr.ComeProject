package com.drcome.project.medical.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.drcome.project.medical.service.DoctorVO;
import com.drcome.project.medical.service.NoticeVO;

public interface HospitalMapper {
	
	/* 대시보드 */
	//진료및예약리스트
	public List<Map<String, Object>> selectTodayReserve(String hospitalId);
	
	//QnA답변O
	public List<Map<String, Object>> selectQnAO(String hospitalId);
	
	//QnA답변X
	public List<Map<String, Object>> selectQnAX(String hospitalId);
	
	/* 환자리스트 */
	//환자 조회
	public List<Map<String, Object>> selectPatientList(Map<String, Object> map);
	
	/* 환자리스트 페이징 */
	public int patientCount(Map<String, Object> map);
	
	//환자 진료 상세 조회
	public List<Map<String, Object>> selectPatientDetailList(String hospitalId, Integer patientNo);
	
	//환자 진료내역 단건 조회
	public Map<String, Object> selectPatientInfo(String hospitalId, Integer patientNo, Integer clinicNo);
	
	//환자 진료내역 단건 처방전 조회
	public List<Map<String, Object>> selectPillList(Integer clinicNo);
	
	/* 예약내역 - clinic */
	//Main
	public List<Map<String, Object>> selectReserveMain(String hospitalId, String date, String reserveKindstatus);
	
	//Dr
	public List<Map<String, Object>> selectReserveDr(String hospitalId, Integer doctorNo, String date, String reserveKindstatus);
	
	//Dr리스트
	public List<Map<String, Object>> allDrList(String hospitalId);
	
	/* QnA */
	//QnA 전체
	public List<Map<String, Object>> selectQnaList(Map<String, Object> map);
	
	/* QnA 리스트 페이징 */
	public int qnaCount(Map<String, Object> map);
	
	//QnA 단건상세
	public List<Map<String, Object>> selectQnaInfo(String hospitalId, Integer qnaNo);
	
	/* 공지사항 */
	//공지사항 전체
	public List<Map<String, Object>> selectNoticeList(@Param("page") int page, @Param("type") int type, @Param("keyword") String keyword, @Param("hospitalId") String hospitalId);
	
	/* 공지사항 리스트 페이징 */
	public int noticeCount(@Param("type") int type, @Param("keyword") String keyword, @Param("hospitalId") String hospitalId);
	
	//공지사항 단건상세
	public List<NoticeVO> selectNoList(String hospitalId, Integer noticeNo);
	
	//공지사항 등록 + 첨부파일 등록
	public int insertNotice(NoticeVO vo);
	public int insertAttach(NoticeVO vo);
	
	//공지사항 검색
//	public List<NoticeVO> searchNoticeByTitle(@Param("page") int page, @Param("hospitalId") String hospitalId, @Param("keyword") String keyword);
//	public List<NoticeVO> searchNoticeByContent(@Param("page") int page, @Param("hospitalId") String hospitalId, @Param("keyword") String keyword);
//	public int searchNoticeCount(@Param("keyword") String keyword, @Param("hospitalId") String hospitalId);
	
	/* 병원프로필 */
	//병원-의사 조회
	public List<DoctorVO> selectDrList(String hospitalId);

	/* 의사 번호 조회 */
	public int getCurrentDoctorNo();
	
	/* 의사 정보 조회 */
	public DoctorVO selectDoctor(int doctorNo);
	
	/* 의사 등록 */
	public int insertDoctor(DoctorVO vo);
	
	/* 의사 시간 등록 */
	public int insertDoctorTime(int doctorNo, String day, String timeSlot);
	
	/* 의사 수정 */
	public int updateDoctor(DoctorVO vo);
	
	/* 의사 시간 삭제 */
	public int deleteDoctorTime(int doctorNo);
}
