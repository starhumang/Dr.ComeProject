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
          // FailureHandler로 예외처리
        } catch (BadCredentialsException e) {
        	
            UserDetails userDetails = getUserDetailsService().loadUserByUsername(authentication.getName());
            
            if (userDetails != null) {
                throw new BadCredentialsException("wrong pw", e);
            } else {
                throw new UsernameNotFoundException("no id", e);
            }
        }
    }

}
