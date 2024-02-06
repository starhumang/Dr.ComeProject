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
    
    //서버접속시 호출 
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	
        sessions.add(session);
    
        String userId = (String) session.getAttributes().get("userId");
        System.out.println("User ID: " + userId);
        System.out.println("긴 ID: " + session.getId());
    }
    
    //메세지 보냈을때 호출 
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        
//        System.out.println("전체 " + session);
    	payload = message.getPayload();
    	System.out.println(payload);
//        String userId = payload;
//        System.out.println("실제 아이디: " + session.getId());
//        System.out.println("받은 아이디: " + userId);
    }

}
