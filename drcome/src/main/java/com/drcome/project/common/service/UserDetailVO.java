package com.drcome.project.common.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDetailVO implements UserDetails {
	
	final MemVO memVO;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority(memVO.getGrade()));
		return list;
	}

	@Override
	public String getPassword() {
		return memVO.getUserPw();
	}

	public String getUsername() {
		return memVO.getUserId();
	}
	
	public String getUserName() {
		return memVO.getUserName();
	}
	
	public String getGrade() {
		return memVO.getGrade();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
