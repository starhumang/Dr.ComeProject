package com.drcome.project.common.mapper;

import com.drcome.project.common.service.UserMemberVO;

public interface UserMemberMapper {

	public UserMemberVO selectUser(String id);
	
	int insertUserMember(UserMemberVO vo);
}
