package com.drcome.project.common.mapper;

import java.util.List;

import com.drcome.project.common.service.UserMemberVO;

public interface UserMemberMapper {

	List<UserMemberVO> selctMemberList(UserMemberVO vo);
	
}
