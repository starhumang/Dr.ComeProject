package com.drcome.project.common.service;

import com.drcome.project.medical.service.HospitalVO;

public interface UserMemberService {
	
	int insertUserMember(UserMemberVO vo);
	
	int insertHosMember(HospitalVO vo);
	
	public MemVO getMember(String id);
	
	public UserMemberVO selectMem(String id);
	
	int checkId(String id);

}
