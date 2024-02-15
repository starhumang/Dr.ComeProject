package com.drcome.project.common.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.drcome.project.common.service.AlarmService;

@Component
public class WebSockChatHandler extends TextWebSocketHandler {

	@Autowired
	AlarmService alarm;

	List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	String payload = "";

	// 모든 세션의 userId 찐 아이디.. 를 담을 배열 생성
	//List<String> userIdList = new ArrayList<>();

	// WebSocket 연결이 성립되면 호출되는 메서드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 연결된 세션을 세션 목록에 추가
		sessions.add(session);

		String userId = (String) session.getAttributes().get("userId");  //http세션아이디
		System.out.println("세션 연결 찐 아이디 User ID: " + userId);
		System.out.println("세션 연결 긴 ID: " + session.getId());

//		// 모든 세션에 대해 userId를 추출하여 리스트에 저장
//		for (WebSocketSession sess : sessions) {
//			userIdList.add((String) sess.getAttributes().get("userId"));
//		}
//
//		// userIdList 출력
//		for (String id : userIdList) {
//			System.out.println("User ID in sessions: " + id);
//		}
//		System.out.println("배열: " + userIdList);
	}// afterConnectionEstablished

	
	// WebSocket으로 메시지가 도착하면 호출되는 메서드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

		// 의사or환자가 담아보낸 아이디 출력
		payload = message.getPayload();
		System.out.println("받은 메세지 ======================" + payload);
		
		// , 기준으로 짤라서 첫번째 = 유저아이디 두번쨰 = 취소사유 세번쨰 셀렉트넘버 서브스트링이나 스플릿
		String[] parts = payload.split("\\,");
		
		String acontent = null;
        String auserId = null;
        String aselectno = null;
        
        if(parts.length >= 3) {
        	auserId = parts[0];
            acontent = parts[1];
            aselectno = parts[2];

            System.out.println(acontent);  // 알람 내용
            System.out.println(auserId); // 알람받을 회원 아이디
            System.out.println(aselectno);  // 취소된 약국 선택 번호
        } else {
        	auserId = payload;
        }
        
		// 의사 카운트
		int cnt = alarm.checkAlarm(auserId);
		// 약국 카운트
		int cntp = alarm.checkAlarmPharmacy(auserId);
        
        for (WebSocketSession sess : sessions) {
			// 여기 equals 뒤에 페이로드는 , 기준으로 짤라서 첫번째 = 유저아이디
			if (sess.getAttributes().get("userId").equals(auserId)) {
				if (cntp > 0) {
					TextMessage sendMsg = new TextMessage("취소 사유:" + acontent + "번호: " + aselectno);
					sess.sendMessage(sendMsg);
					System.out.println("보냈음!!!!!!!!!!!!!!!!!!!!!!");
				} else if (cnt > 0) {					
					TextMessage sendMsg = new TextMessage(sess.getAttributes().get("userId") + "님..진료실..입장하세요..");
					sess.sendMessage(sendMsg);
				}
			}
		}

//		// 의사 카운트
//		int cnt = alarm.checkAlarm(auserId);
//
//		System.out.println("알람조회결과는여?" + cnt);
//
//		if (cnt > 0) {
//			for (WebSocketSession sess : sessions) {
//				// 여기 equals 뒤에 페이로드는 , 기준으로 짤라서 첫번째 = 유저아이디
//				if (sess.getAttributes().get("userId").equals(auserId)) {
//					// new TextMessage 안에는 , 기준 두번쨰 세번째
//					TextMessage sendMsg = new TextMessage(sess.getAttributes().get("userId") + "님..진료실..입장하세요..");
//					System.out.println("보내는말: "+ sendMsg);
//					sess.sendMessage(sendMsg);
//				}
//
//			}
//		} // if
//		
//		// 약국 카운트
//		int cntp = alarm.checkAlarmPharmacy(auserId);
//		
//		System.out.println("약국 알람조회결과는여?" + cntp);
//		
//		System.out.println("아이디:" + auserId);
//		
//		if (cntp > 0) {
//			for (WebSocketSession sess : sessions) {
//				// 여기 equals 뒤에 페이로드는 , 기준으로 짤라서 첫번째 = 유저아이디
//				if (sess.getAttributes().get("userId").equals(auserId)) {
//					// new TextMessage 안에는 , 기준 두번쨰 세번째
//					TextMessage sendMsg = new TextMessage("약국 취소 사유: " + acontent);
//					System.out.println("보내는말: "+ sendMsg);
//					sess.sendMessage(sendMsg);
//				}
//
//			}
//		} // if
		

	}/// handleTextMessage

	// WebSocket 연결이 닫혔을 때 호출되는 메서드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 세션 목록에서 연결이 종료된 세션을 제거
		sessions.remove(session);
		// userIdList에서 해당 세션의 userId를 제거
		//userIdList.remove(session.getAttributes().get("userId"));
		System.out.println("연결이 종료된 세션 ID: " + session.getAttributes().get("userId"));
	}

}
