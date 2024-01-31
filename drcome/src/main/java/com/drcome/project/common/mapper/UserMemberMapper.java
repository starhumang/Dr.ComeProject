package com.drcome.project.common.mapper;

import com.drcome.project.common.service.UserMemberVO;

public interface UserMemberMapper {

	int insertUserMember(UserMemberVO vo);
	
	public UserMemberVO selectUser(String id);
}
