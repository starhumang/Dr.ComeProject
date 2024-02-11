package com.drcome.project;

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

	// 모든 세션의 userId를 담을 배열 생성
	List<String> userIdList = new ArrayList<>();

	// WebSocket 연결이 성립되면 호출되는 메서드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 연결된 세션을 세션 목록에 추가
		sessions.add(session);

		String userId = (String) session.getAttributes().get("userId");
		System.out.println("세션 연결 찐 아이디 User ID: " + userId);
		System.out.println("세션 연결 긴 ID: " + session.getId());

		// 모든 세션에 대해 userId를 추출하여 리스트에 저장
		for (WebSocketSession sess : sessions) {
			userIdList.add((String) sess.getAttributes().get("userId"));
		}

		// userIdList 출력
		for (String id : userIdList) {
			System.out.println("User ID in sessions: " + id);
		}
		System.out.println("배열: " + userIdList);
	}// afterConnectionEstablished

	// WebSocket으로 메시지가 도착하면 호출되는 메서드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

		// 의사가 담아보낸 아이디 출력
		payload = message.getPayload();
		System.out.println("의사가 보낸 환자아이디 " + payload);

		int cnt = alarm.checkAlarm(payload);

		System.out.println("알람조회결과는여?" + cnt);

		if (cnt > 0) {
			for (WebSocketSession sess : sessions) {
				if (sess.getAttributes().get("userId").equals(payload)) {
					TextMessage sendMsg = new TextMessage(sess.getAttributes().get("userId") + "님..진료실..입장하세요..");
					System.out.println(sess.getAttributes().get("userId") + "님..진료실..입장하세요..");
					sess.sendMessage(sendMsg);
				}

			}

		} // if

	}/// handleTextMessage

	// WebSocket 연결이 닫혔을 때 호출되는 메서드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 세션 목록에서 연결이 종료된 세션을 제거
		sessions.remove(session);
		// userIdList에서 해당 세션의 userId를 제거
		userIdList.remove(session.getAttributes().get("userId"));
		System.out.println("연결이 종료된 세션 ID: " + session.getAttributes().get("userId"));
	}

}
