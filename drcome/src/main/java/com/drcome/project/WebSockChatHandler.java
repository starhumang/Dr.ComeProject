package com.drcome.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    
    List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
    String payload = "";

    // 모든 세션의 userId를 담을 배열 생성
    List<String> userIdList = new ArrayList<>();
    
    // WebSocket 연결이 성립되면 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 연결된 세션을 세션 목록에 추가
        sessions.add(session);
        
        // HttpSession에서 userId 가져와서 WebSocketSession 속성에 설정
        String userId = (String) session.getAttributes().get("userId");
        System.out.println("User ID: " + userId);
        System.out.println("긴 ID: " + session.getId());
        
        // 모든 세션에 대해 userId를 추출하여 리스트에 저장
        for (WebSocketSession sess : sessions) {
            userIdList.add((String) sess.getAttributes().get("userId"));
        }
        
        // userIdList 출력
        for (String id : userIdList) {
            System.out.println("User ID in sessions: " + id);
        }
        System.out.println("배열: "+ userIdList);
    }
    
    // WebSocket으로 메시지가 도착하면 호출되는 메서드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // 받은 메시지 출력
        payload = message.getPayload();
        System.out.println(payload);
        System.out.println("보낼 아이디: " + userIdList);
        System.out.println("세션" + session);
        
        for (WebSocketSession sess : sessions) {
        	System.out.println("세션 긴아이디 " + sess.getId());
        	System.out.println("세션 찐아이디 " + sess.getAttributes().get("userId"));
        	if(sess.getAttributes().get("userId").equals("test3")) {
//        		TextMessage sendMsg = new TextMessage(sess.getAttributes().get("userId") + "야 안녕?");
        		TextMessage sendMsg = new TextMessage("입장하세요!!!!!!!!!!!!!!!");
        		sess.sendMessage(sendMsg);
        	}
//        	sess.sendMessage(new TextMessage("안녕?"));
        }
    }
    
    // WebSocket 연결이 닫혔을 때 호출되는 메서드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 세션 목록에서 연결이 종료된 세션을 제거
        sessions.remove(session);
        for (WebSocketSession sess : sessions) {
        	sessions.remove(sess);
        }
        //userIdList에서 해당 세션의 userId를 제거
        userIdList.remove(session.getAttributes().get("userId"));
        System.out.println("연결이 종료된 세션 ID: " + session.getId());
    }

}
