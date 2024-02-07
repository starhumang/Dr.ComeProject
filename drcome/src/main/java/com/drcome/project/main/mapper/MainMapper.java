package com.drcome.project.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.drcome.project.main.service.ReservationVO;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

public interface MainMapper {
	
	//병원리스트
	public List<HospitalVO> selectHosList();
	
	//약국리스트
	public List<PharmacyVO> selectPhaList(@Param("num") int num);
	
	//병원상세정보
	public HospitalVO selectHos(String hospitalId);
	
	
	//약국상세정보
	public PharmacyVO selectPha(String pharmacyId);
	
	//병원검색
	/*참고용 : public List<RestaurantVO> selectSearch(@Param("word") String word);*/
	public List<HospitalVO> searchHosList(@Param("word") String word);
	
	//약국검색
	public List<PharmacyVO> searchPhaList(@Param("word") String word);
	
	//병원진료과목 검색
	public List<HospitalVO> searchSubjectHos(String mainSubject);
	
	//처방받을 약국 선택(insert)
	public int insertPhaSelect(@Param("pharmacyId")String pharmacyId, @Param("clinicNo")int clinicNo);
	
	//예약전 초진기록 확인(예약버튼활성화)
	public int checkClinicHistory(@Param("userId")String userId, @Param("hospitalId")String hospitalId);
	
	//예약전 해당병원에 금일 예약내역이 있는지 보고 아직 예약시간이 안 지났으면 지날때까지 해당병원 예약금지 시키기
	public int checkReservationHistory(@Param("userId")String userId, @Param("hospitalId")String hospitalId);
	
	//방문예약 (insert)
	public int insertContactReservation(ReservationVO reservationVo);
	
}
