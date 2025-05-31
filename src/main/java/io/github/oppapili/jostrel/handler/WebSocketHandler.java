package io.github.oppapili.jostrel.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.oppapili.jostrel.model.Message;
import io.github.oppapili.jostrel.model.MessageDeserializer;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    private final ObjectMapper objectMapper;

    public WebSocketHandler() {
        this.objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Message.class, new MessageDeserializer());
        objectMapper.registerModule(module);
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        logger.debug("📡 WebSocket connection established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session,
            @NonNull TextMessage message) throws Exception {
        try {
            String payload = message.getPayload();
            Message msg = objectMapper.readValue(payload, Message.class);
            logger.debug("Received message: " + msg);

            // response echo message
            session.sendMessage(new TextMessage("Echo: " + payload));
        } catch (Exception e) {
            logger.error("❌ Error processing message: " + e.getMessage(), e);
            throw new RuntimeException("Error processing message", e);
        }
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session,
            @NonNull CloseStatus status) throws Exception {
        logger.debug("📡 WebSocket Connection closed: " + session.getId());
    }
}
