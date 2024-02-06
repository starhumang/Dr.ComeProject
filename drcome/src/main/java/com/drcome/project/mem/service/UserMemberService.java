package com.drcome.project.mem.service;

import java.util.Map;

import com.drcome.project.medical.service.HospitalVO;

public interface UserMemberService {
	
	int insertUserMember(UserMemberVO vo);
	
	int insertHosMember(HospitalVO vo);
	
	public MemVO getMember(String id);
	
	public UserMemberVO selectMem(String id);
	
	int checkId(String id);
	
	int checkPhone(String phone);
	
	public Map<String,Object> sendNumber(String phoneNum);

	String findId(String userName, String phone);
	
	int updatePw(MemVO vo);
	
	int updateUserInfo(UserMemberVO vo);
}
