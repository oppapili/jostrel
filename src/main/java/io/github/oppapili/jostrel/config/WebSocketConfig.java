package io.github.oppapili.jostrel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import io.github.oppapili.jostrel.handler.WebSocketHandler;

/**
 * WebSocket configuration class for the Jostrel application.
 * 
 * <p>
 * This class configures the WebSocket endpoint and registers the WebSocket handler. It allows
 * WebSocket connections to be established at the root path ("/").
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Autowired
    public WebSocketConfig(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/").setAllowedOrigins("*");
    }
}
