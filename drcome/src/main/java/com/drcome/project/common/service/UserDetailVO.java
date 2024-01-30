package com.drcome.project.common.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDetailVO extends UserMemberVO implements UserDetails {
	
	public UserDetailVO(UserMemberVO userMemVO) {
        super(userMemVO.getUserId(), userMemVO.getUserPw(), userMemVO.getUserName(),
              userMemVO.getPhone(), userMemVO.getBirthday(), userMemVO.getGender(),
              userMemVO.getIdentification(), userMemVO.getJoinDate(), userMemVO.getGrade(),
              userMemVO.getUserStatus());
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority(super.getGrade()));
		return list;
	}

	@Override
	public String getPassword() {
		return super.getUserPw();
	}

	@Override
	public String getUsername() {
		return super.getUserName();
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
