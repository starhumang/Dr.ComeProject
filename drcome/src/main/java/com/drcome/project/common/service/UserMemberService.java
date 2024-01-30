package com.drcome.project.common.service;

public interface UserMemberService {
	
	int insertUserMember(UserMemberVO vo);
	
	public UserMemberVO selectUser(String id);

}
