package io.github.oppapili.jostrel.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.oppapili.jostrel.model.Filter;
import io.github.oppapili.jostrel.model.Message;
import io.github.oppapili.jostrel.model.MessageDeserializer;
import io.github.oppapili.jostrel.model.Subscription;
import io.github.oppapili.jostrel.service.SubscriptionManager;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    private final ObjectMapper objectMapper;

    @Autowired
    private SubscriptionManager subscriptionManager;

    public WebSocketHandler(SubscriptionManager subscriptionManager) {
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
            Message nostrMsg = objectMapper.readValue(message.getPayload(), Message.class);

            var payload = nostrMsg.getPayload();
            switch (nostrMsg.getType()) {
                case EVENT:
                    // Handle event message
                    logger.debug("Received event: " + nostrMsg.getPayload());
                    break;

                case REQ:
                    // Handle subscription request
                    // payload: [<subscription_id>, <filters1>, <filters2>, ...]
                    var filters = payload.subList(1, payload.size()).stream()
                            .map(node -> objectMapper.convertValue(node, Filter.class)).toList();
                    var subscription = Subscription.builder().id(payload.get(0).asText())
                            .filters(filters).build();
                    subscriptionManager.addSubscriptionToSession(session.getId(), subscription);
                    break;

                case CLOSE:
                    // Handle unsubscribe request
                    // payload: [<subscription_id>]
                    subscriptionManager.removeSubscriptionFromSession(session.getId(),
                            payload.get(0).asText());
                    break;

                default:
                    logger.warn("Unknown message type: " + nostrMsg.getType());
            }
        } catch (Exception e) {
            logger.error("❌ Error processing message: " + e.getMessage(), e);
            throw new RuntimeException("Error processing message", e);
        }
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session,
            @NonNull CloseStatus status) throws Exception {
        subscriptionManager.removeAllSubscriptionsOfSession(session.getId());

        logger.debug("📡 WebSocket Connection closed: " + session.getId());
    }
}
