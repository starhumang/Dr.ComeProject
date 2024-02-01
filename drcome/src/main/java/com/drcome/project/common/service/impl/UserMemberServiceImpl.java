package com.drcome.project.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.drcome.project.common.mapper.UserMemberMapper;
import com.drcome.project.common.service.MemVO;
import com.drcome.project.common.service.UserDetailVO;
import com.drcome.project.common.service.UserMemberService;
import com.drcome.project.common.service.UserMemberVO;
import com.drcome.project.medical.service.HospitalVO;

@Service
public class UserMemberServiceImpl implements UserMemberService, UserDetailsService {

	@Autowired
	UserMemberMapper mapper;
	
	@Override
	public int insertUserMember(UserMemberVO vo) {
		return mapper.insertUserMember(vo);
	}
	
	@Override
	public int insertHosMember(HospitalVO vo) {
		return mapper.insertHosMember(vo);
	}
	
	@Override
	public MemVO getMember(String id) {
		return mapper.getMember(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	    
	    MemVO vo = mapper.getMember(username);
	    if (vo == null) {
			throw new UsernameNotFoundException("no user");
		}
	    return new UserDetailVO(vo);
	}

}