package com.drcome.project.common.service;

import java.util.List;

public interface UserMemberService {
	
	List<UserMemberVO> selectMemberList(UserMemberVO vo);
	
	int insertUserMember(UserMemberVO vo);

}
