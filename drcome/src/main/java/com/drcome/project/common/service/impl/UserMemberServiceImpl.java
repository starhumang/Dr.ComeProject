package com.drcome.project.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.common.mapper.UserMemberMapper;
import com.drcome.project.common.service.UserMemberService;
import com.drcome.project.common.service.UserMemberVO;

@Service
public class UserMemberServiceImpl implements UserMemberService{
	
	@Autowired
	private UserMemberMapper mapper;
	
	@Override
	public List<UserMemberVO> selectMemberList(UserMemberVO vo) {
		return mapper.selectMemberList(vo);
	}
	
	@Override
	public int insertUserMember(UserMemberVO vo) {
		return mapper.insertUserMember(vo);
	}

}
