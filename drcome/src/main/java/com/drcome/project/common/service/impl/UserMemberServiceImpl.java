package com.drcome.project.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.drcome.project.common.mapper.UserMemberMapper;
import com.drcome.project.common.service.UserDetailVO;
import com.drcome.project.common.service.UserMemberService;
import com.drcome.project.common.service.UserMemberVO;

@Service
public class UserMemberServiceImpl implements UserMemberService, UserDetailsService {

	@Autowired
	UserMemberMapper mapper;

	@Override
	public int insertUserMember(UserMemberVO vo) {
		return mapper.insertUserMember(vo);
	}

	@Override
	public UserMemberVO selectUser(String id) {
		return mapper.selectUser(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
	    UserMemberVO vo = mapper.selectUser(username);
	    if(vo == null) {
	        throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + username);
	    }
	    return new UserDetailVO(vo);
	}

}
