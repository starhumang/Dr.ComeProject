package com.drcome.project.common.mapper;

import com.drcome.project.common.service.MemVO;
import com.drcome.project.common.service.UserMemberVO;
import com.drcome.project.medical.service.HospitalVO;

public interface UserMemberMapper {

	int insertUserMember(UserMemberVO vo);
	
	int insertHosMember(HospitalVO vo);
	
	public MemVO getMember(String id);
	
	public UserMemberVO selectMem(String id);
}
