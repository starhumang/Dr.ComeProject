package com.drcome.project.main.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.drcome.project.medical.service.DoctorVO;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

@Service
public interface MainService {
	
	//병원목록
	public List<HospitalVO> getHosList();
	
	//약국목록
	public List<PharmacyVO> getPhaList();
	
	//병원상세정보
	public HospitalVO getHos(String hospitalId);
	
	//약국상세정보
	public PharmacyVO getPha(String pharmacyId);
	
	//병원검색
	public List<HospitalVO> searchHosList(String word);
	
	//약국검색
	public List<PharmacyVO> searchPhaList(String word);

	//병원진료과목 검색
	public List<HospitalVO> searchSubjectHos(String mainSubject);
	
	//추천받은 약국리스트
	public List<PharmacyVO> recommendPhaList(int num);
	
	//처방받을 약국 선택(insert)
	public int insertPhaSelect(String pharmacyId, int clinicNo);
	
	//예약전 초진기록 확인(예약버튼활성화)
	public int checkClinicHistory(String userId, String hospitalId);
	
	//예약전 해당병원에 금일 예약내역이 있는지 보고 아직 예약시간이 안 지났으면 지날때까지 해당병원 예약금지 시키기
	public int checkReservationHistory(String userId, String hospitalId);
	
	//방문예약 (insert)
	public int insertContactReservation(ReservationVO reservationVo);
	
	//비대면예약 (insert)
	public int insertUntactReservation(ReservationVO reservationVo);
	
	//선택한 의사의 예약시간뽑기(클릭못하게 처리)
	public List<ReservationVO> findreserveListToChoice(ReservationVO reservationVo);
	
	//한 의사에 대한 오늘날짜의 현재시간 이후 모든 예약리스트
	public List<ReservationVO> findWaitingList(DoctorVO doctorVO);
	
}
