package com.drcome.project.common.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class UserMemberServiceImpl implements UserMemberService, UserDetailsService {

	@Autowired
	UserMemberMapper mapper;
	
	//COOLSMS
    @Value("${coolsms.apiKey}")
    private String coolSmsApiKey;

    @Value("${coolsms.apiSecret}")
    private String coolSmsApiSecret;
    
    @Value("${coolsms.fromNumber}")
    private String coolSmsFromNumber;
    
    private DefaultMessageService messageService;

    @PostConstruct
    public void init() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize(coolSmsApiKey, coolSmsApiSecret, "https://api.coolsms.co.kr");
    }
	
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
	public UserMemberVO selectMem(String id) {
		return mapper.selectMem(id);
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