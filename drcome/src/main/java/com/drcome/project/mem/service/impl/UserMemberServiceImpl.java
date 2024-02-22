package com.drcome.project.mem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.drcome.project.main.service.PaymentVO;
import com.drcome.project.main.service.ReservationVO;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.mem.mapper.UserMemberMapper;
import com.drcome.project.mem.service.MemVO;
import com.drcome.project.mem.service.UserDetailVO;
import com.drcome.project.mem.service.UserMemberService;
import com.drcome.project.mem.service.UserMemberVO;
import com.drcome.project.pharmacy.PharmacyVO;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class UserMemberServiceImpl implements UserMemberService, UserDetailsService {

	@Autowired
	UserMemberMapper mapper;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
		
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
    
//  사용자 조회 - 시큐리티
	@Override
	public MemVO getMember(String id) {
		return mapper.getMember(id);
	}
	
//	일반 사용자 정보 조회
	@Override
	public UserMemberVO selectMem(String id) {
		return mapper.selectMem(id);
	}
	
//  일반 사용자 회원 가입
	@Override
	public int insertUserMember(UserMemberVO vo) {
		return mapper.insertUserMember(vo);
	}
	
//	병원 사용자 회원 가입
	@Override
	public int insertHosMember(HospitalVO vo) {
		return mapper.insertHosMember(vo);
	}
	
//	약국 사용자 회원 가입
	@Override
	public int insertPamMember(PharmacyVO vo) {
		return mapper.insertPamMember(vo);
	}
	
//	아이디 중복 체크
	@Override
	public int checkId(String id) {
		return mapper.checkId(id);
	}
	
//	전화번호 중복 체크
	@Override
	public int checkPhone(String phone) {
		return mapper.checkPhone(phone);
	}
	
//	인증번호 발송
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
	
//	아이디 찾기
	@Override
	public String findId(String userName, String phone) {
		return mapper.findId(userName, phone);
	}
	
//	비밀번호 찾기 - 변경
	@Override
	public int updatePw(MemVO vo) {
		return mapper.updatePw(vo);
	}
	
//	일반 사용자 정보 변경
	@Override
	public int updateUserInfo(UserMemberVO vo) {
		return mapper.updateUserInfo(vo);
	}

//	병원 사용자 정보 변경
	@Override
	public int updateHosInfo(HospitalVO vo) {
		return mapper.updateHosInfo(vo);
	}
	
//	약국 사용자 정보 변경
	@Override
	public int updatePamInfo(PharmacyVO vo) {
		return mapper.updatePamInfo(vo);
	}
	
//	회원 탈퇴
	@Override
	public int deleteUser(String userId, String password, Authentication auth) {
		MemVO user = mapper.getMember(userId);
        
        // 사용자 정보가 없거나 비밀번호가 일치하지 않으면 탈퇴 실패로 처리
        if (user == null || !bCryptPasswordEncoder.matches(password, user.getUserPw())) {
            return -1; // 탈퇴 실패
        }
        
        // 탈퇴 처리        
        return mapper.deleteUser(user);
	}
	
	@Override
	@Transactional
	public int paymentUpdate(PaymentVO vo) {
		
		// 예약 번호
		int reserveNo = vo.getReserveNo();
		
		// 예약 테이블 업데이트
		int result = mapper.updateReserve(reserveNo); 
		
		// 결제 테이블 인서트
		result = mapper.insertPayment(vo);
		
		// 결제 번호
		int paymentNo = vo.getPaymentNo();
		
		// 진료 테이블 업데이트
		result = mapper.updatePayment(reserveNo, paymentNo);
		
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getUserQnaList(Map<String, Object> map) {
		List<Map<String, Object>> listQnaAll = mapper.selectUserQnaList(map);
		return listQnaAll;
	}
	
	@Override
	public int qnaUserCount(Map<String, Object> map) {
		return mapper.qnaUserCount(map);
	}

//	시큐리티 로그인 검증
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	    
	    MemVO vo = mapper.getMember(username);
	    //a3: 탈퇴회원
	    if (vo == null || vo.getUserStatus().equals("a3")) {
			throw new UsernameNotFoundException("no user");
		}
	    return new UserDetailVO(vo);
	}

}