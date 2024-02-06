package com.drcome.project.mem.mapper;

import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.mem.service.MemVO;
import com.drcome.project.mem.service.UserMemberVO;

public interface UserMemberMapper {

	int insertUserMember(UserMemberVO vo);
	
	int insertHosMember(HospitalVO vo);
	
	public MemVO getMember(String id);
	
	public UserMemberVO selectMem(String id);
	
	int checkId(String id);
	
	int checkPhone(String phone);

	String findId(String userName, String phone);
	
	int updatePw(MemVO vo);
	
	int updateUserInfo(UserMemberVO vo);
}
