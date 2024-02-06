package com.drcome.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    
    List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
    String payload = "";
    
    // WebSocket 연결이 성립되면 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 연결된 세션을 세션 목록에 추가
        sessions.add(session);
        
        // HttpSession에서 userId 가져와서 WebSocketSession 속성에 설정
        String userId = (String) session.getAttributes().get("userId");
        System.out.println("User ID: " + userId);
        System.out.println("긴 ID: " + session.getId());
        // 모든 세션의 userId를 담을 배열 생성
        String[] userIdarr = new String[sessions.size()];
        
        // 모든 세션에 대해 userId를 추출하여 배열에 저장
        for (int i = 0; i < sessions.size(); i++) {
            userIdarr[i] = (String) sessions.get(i).getAttributes().get("userId");
        }
        
        // userIdarr 배열 출력
        for (String id : userIdarr) {
            System.out.println("User ID in sessions: " + id);
        }
    }
    
    // WebSocket으로 메시지가 도착하면 호출되는 메서드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // 받은 메시지 출력
        payload = message.getPayload();
        System.out.println(payload);
    }

}
