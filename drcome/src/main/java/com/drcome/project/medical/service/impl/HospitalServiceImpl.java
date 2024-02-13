package com.drcome.project.medical.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.medical.mapper.HospitalMapper;
import com.drcome.project.medical.repository.HospitalRepository;
import com.drcome.project.medical.service.DoctorTimeVO;
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

	/* 환자리스트 */
	// 환자 조회
	@Override
	public List<Map<String, Object>> getPaientList(Map<String, Object> map) {
		List<Map<String, Object>> listPa = hospitalMapper.selectPatientList(map);
		return listPa;
	}
	
	// 환자 페이징
	@Override
	public int patientCount(Map<String, Object> map) {
		return hospitalMapper.patientCount(map);
	}

	// 환자 상세 조회
	@Override
	public List<Map<String, Object>> getPaientDetailList(Map<String, Object> map) {
		List<Map<String, Object>> listPaDe = hospitalMapper.selectPatientDetailList(map);
		return listPaDe;
	}
	
	// 환자 상세 페이징
	@Override
	public int patientInfoCount(Map<String, Object> map) {
		return hospitalMapper.patientInfoCount(map);
	}

	// 환자 진료내역 단건 조회
	@Override
	public Map<String, Object> getPaientClinicInfo(String hospitalId, Integer patientNo, Integer clinicNo) {
		Map<String, Object> listClinicInfo = hospitalMapper.selectPatientInfo(hospitalId, patientNo, clinicNo);
		return listClinicInfo;
	}

	// 환자 진료내역 단건 처방전 조회
	@Override
	public List<Map<String, Object>> getpaientPillInfo(Integer clinicNo) {
		List<Map<String, Object>> listClinicPill = hospitalMapper.selectPillList(clinicNo);
		return listClinicPill;
	}

	/* 예약내역 - clinic */
	//Main
	@Override
	public List<Map<String, Object>> getRerveList(String hospitalId, String date, String reserveKindstatus) {
		List<Map<String, Object>> listReserveAll = hospitalMapper.selectReserveMain(hospitalId, date, reserveKindstatus);
		return listReserveAll;
	}

	//Dr
	@Override
	public List<Map<String, Object>> getReserveDrList(String hospitalId, Integer doctorNo, String date, String reserveKindstatus) {
		List<Map<String, Object>> listReserveDr = hospitalMapper.selectReserveDr(hospitalId, doctorNo, date, reserveKindstatus);
		return listReserveDr;
	}
	//Dr리스트

	@Override
	public List<Map<String, Object>> getDrAllList(String hospitalId) {
		List<Map<String, Object>> listDrAll = hospitalMapper.allDrList(hospitalId);
		return listDrAll;
	}
	
	/* QnA */
	// QnA 전체
	@Override
	public List<Map<String, Object>> getQnaList(Map<String, Object> map) {
		List<Map<String, Object>> listQnaAll = hospitalMapper.selectQnaList(map);
		return listQnaAll;
	}
	
	// QnA 페이징
	@Override
	public int qnaCount(Map<String, Object> map) {
		return hospitalMapper.qnaCount(map);
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
	public List<Map<String, Object>> getNoticeList(int page, int type, String keyword, String hospitalId) {
		List<Map<String, Object>> listNoticeAll = hospitalMapper.selectNoticeList(page, type, keyword, hospitalId);
		return listNoticeAll;
	}
	
	// 공지사항 페이징
	@Override
	public int noticeCount(int type, String keyword, String hospitalId) {
		return hospitalMapper.noticeCount(type, keyword, hospitalId);
	}
	
	// 공지사항 단건상세
	@Override
	public NoticeVO getNoticeDetail(NoticeVO noticeVO) {
		NoticeVO noticeInfo = hospitalMapper.selectNoList(noticeVO);
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
	
	// 공지사항 수정 + 첨부파일
	@Override
	public int updateNotice(NoticeVO vo) {
//		int result = 0;
//		int noticeNo = vo.getNoticeNo();
//		result = hospitalMapper.deleteAttachment(noticeNo);
		return hospitalMapper.updateNotice(vo);
	}
	@Override
	public int deleteAttachment(int noticeNo) {
		return hospitalMapper.deleteAttachment(noticeNo);
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

	// 의사 번호 조회
	@Override
	public int getCurrentDoctorNo() {
		return hospitalMapper.getCurrentDoctorNo();
	}
	
	// 의사 정보 조회
	@Override
	public DoctorVO selectDoctor(int doctorNo) {
		return hospitalMapper.selectDoctor(doctorNo);
	}

	// 의사 등록
	@Override
	public int insertDoctor(DoctorVO vo) {
        int count = hospitalMapper.insertDoctor(vo);
        
        int doctorNo = hospitalMapper.getCurrentDoctorNo();

        List<DoctorTimeVO> times = vo.getTimes();
        for (DoctorTimeVO time : times) {
            String day = time.getDay();
            List<String> timeArray = time.getTimeArray();
            for (String timeSlot : timeArray) {
                hospitalMapper.insertDoctorTime(doctorNo, day, timeSlot);
            }
        }
        return count;
	}
	
	// 의사 수정
	@Override
	public int updateDoctor(DoctorVO vo) {
		int count = hospitalMapper.updateDoctor(vo);
		
		int doctorNo = vo.getDoctorNo();
		
		hospitalMapper.deleteDoctorTime(doctorNo);
		
		List<DoctorTimeVO> times = vo.getTimes();
        for (DoctorTimeVO time : times) {
            String day = time.getDay();
            List<String> timeArray = time.getTimeArray();
            for (String timeSlot : timeArray) {
                hospitalMapper.insertDoctorTime(doctorNo, day, timeSlot);
            }
        }
		return count;
	}



}
