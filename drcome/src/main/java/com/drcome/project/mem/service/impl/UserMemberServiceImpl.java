package com.drcome.project.mem.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.mem.mapper.UserMemberMapper;
import com.drcome.project.mem.service.MemVO;
import com.drcome.project.mem.service.UserDetailVO;
import com.drcome.project.mem.service.UserMemberService;
import com.drcome.project.mem.service.UserMemberVO;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
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
	public int checkId(String id) {
		return mapper.checkId(id);
	}
	
	@Override
	public int checkPhone(String phone) {
		return mapper.checkPhone(phone);
	}
	
	@Override
	public String findId(String userName, String phone) {
		return mapper.findId(userName, phone);
	}
	
	@Override
	public int updatePw(MemVO vo) {
		return mapper.updatePw(vo);
	}
	
	@Override
	public int updateUserInfo(UserMemberVO vo) {
		return mapper.updateUserInfo(vo);
	}
	
	@Override
	public Map<String, Object> sendNumber(String phoneNum) {
		Map<String,Object> result = new HashMap<String,Object>();
		int checkNum = (int) (Math.random() * 9000) + 1000;
		
		result.put("checkNum", checkNum);
		
//		Message message = new Message();
//		message.setFrom(coolSmsFromNumber);
//    	message.setTo(phoneNum);
//    	message.setText("[DrCome] 인증번호: "+checkNum);
//    
//    	SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));        
//    	result.put("result", response);
//    	result.put("checkNum", checkNum);

		return result;
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