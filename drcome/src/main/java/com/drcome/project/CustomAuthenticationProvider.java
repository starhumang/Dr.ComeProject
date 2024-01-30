package com.drcome.project;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
        setUserDetailsService(userDetailsService);
        setPasswordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        try {
            return super.authenticate(authentication);
        } catch (BadCredentialsException e) {
            // 패스워드가 잘못된 경우, 사용자가 존재하는지 확인하고 UsernameNotFoundException을 던집니다.
            UserDetails userDetails = getUserDetailsService().loadUserByUsername(authentication.getName());
            if (userDetails != null) {
                throw new BadCredentialsException("비밀번호 틀림", e);
            } else {
                throw new UsernameNotFoundException("아이디 없음", e);
            }
        }
    }

}
