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
        
    	//의사가 보낸 메세지 
    	payload = message.getPayload();
    	System.out.println(payload);
        //String userId = payload;

    		//payload 아이디로 db 조회 해서 
    		//아직 알람이 체크가 되지 않았고 
    		//페이로드 안에있는 id ==  for문(세션아이디)가 같으면 
    	
    
    	//send mes 
    	
    }

}
