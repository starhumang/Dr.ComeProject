package com.drcome.project;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    
    private final WebSockChatHandler webSockChatHandler;
    private final HttpSessionToWebSocketInterceptor httpSessionToWebSocketInterceptor;

    public WebSocketConfig(WebSockChatHandler webSockChatHandler, HttpSessionToWebSocketInterceptor httpSessionToWebSocketInterceptor) {
        this.webSockChatHandler = webSockChatHandler;
        this.httpSessionToWebSocketInterceptor = httpSessionToWebSocketInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSockChatHandler, "/echo").setAllowedOrigins("*").addInterceptors(httpSessionToWebSocketInterceptor);
    }
}
