package com.drcome.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.drcome.project.common.web.HttpSessionToWebSocketInterceptor;
import com.drcome.project.common.web.WebSockChatHandler;

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
