package com.drcome.project.mem.mapper;

import java.util.List;
import java.util.Map;

import com.drcome.project.main.service.PaymentVO;
import com.drcome.project.main.service.ReservationVO;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.mem.service.MemVO;
import com.drcome.project.mem.service.UserMemberVO;
import com.drcome.project.pharmacy.PharmacyVO;

public interface UserMemberMapper {

//	사용자 조회 - 시큐리티
	public MemVO getMember(String id);
	
//	일반 사용자 정보 조회
	public UserMemberVO selectMem(String id);
	
//	일반 사용자 회원 가입
	int insertUserMember(UserMemberVO vo);
	
//	병원 사용자 회원 가입
	int insertHosMember(HospitalVO vo);
	
//	약국 사용자 회원 가입
	int insertPamMember(PharmacyVO vo);
	
//	아이디 중복 체크
	int checkId(String id);
	
//	전화번호 중복 체크
	int checkPhone(String phone);

//	아이디 찾기
	String findId(String userName, String phone);
	
//	비밀번호 찾기 - 변경
	int updatePw(MemVO vo);
	
//	일반 사용자 정보 변경
	int updateUserInfo(UserMemberVO vo);
	
//	병원 사용자 정보 변경
	int updateHosInfo(HospitalVO vo);
	
//	약국 사용자 정보 변경
	int updatePamInfo(PharmacyVO vo);
	
//	회원 탈퇴
	int deleteUser(MemVO vo);
	
//	예약 조회
	public List<Map<String, Object>> selectTodayReserve(String userId);
	
//	예약 조회
	public List<ReservationVO> selectReserveInfo(String userId);
	
//	결제
	int insertPayment(PaymentVO vo);
}
