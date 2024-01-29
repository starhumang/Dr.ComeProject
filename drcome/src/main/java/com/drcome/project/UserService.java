package com.drcome.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.drcome.project.common.mapper.UserMemberMapper;
import com.drcome.project.common.service.UserMemberVO;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	UserMemberMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserMemberVO vo = new UserMemberVO();
		
		vo.setUserId(username);
		vo = mapper.selectUser(vo);
		
		if (vo == null) {
			throw new UsernameNotFoundException("no user");
		}
		
		return vo;
	}
}
