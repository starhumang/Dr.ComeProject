package com.drcome.project.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

public interface MainMapper {
	
	//병원리스트
	public List<HospitalVO> selectHosList();
	
	//약국리스트
	public List<PharmacyVO> selectPhaList();
	
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
	
}
