package io.github.oppapili.jostrel.handler;

import java.io.IOException;
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
import io.github.oppapili.jostrel.model.ClosedMessage;
import io.github.oppapili.jostrel.model.EoseMessage;
import io.github.oppapili.jostrel.model.Event;
import io.github.oppapili.jostrel.model.EventMessage;
import io.github.oppapili.jostrel.model.Filter;
import io.github.oppapili.jostrel.model.Message;
import io.github.oppapili.jostrel.model.Subscription;
import io.github.oppapili.jostrel.service.EventService;
import io.github.oppapili.jostrel.service.SubscriptionManager;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SubscriptionManager subscriptionManager;
    @Autowired
    private EventService eventService;

    public WebSocketHandler(ObjectMapper objectMapper, SubscriptionManager subscriptionManager,
            EventService eventService) {
        this.objectMapper = objectMapper;
        this.subscriptionManager = subscriptionManager;
        this.eventService = eventService;
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
                    // payload: [<event>]
                    var event = objectMapper.convertValue(payload, Event.class);

                    // save the event to the database
                    eventService.saveEvent(event);
                    break;

                case REQ:
                    // Handle subscription request
                    // payload: [<subscription_id>, <filters1>, <filters2>, ...]
                    var filters = payload.subList(1, payload.size()).stream()
                            .map(node -> objectMapper.convertValue(node, Filter.class)).toList();
                    var subscription = Subscription.builder().id(payload.get(0).asText())
                            .filters(filters).build();

                    // Add subscription to the session
                    subscriptionManager.addSubscriptionToSession(session.getId(), subscription);

                    // Send matching events for the subscription filters
                    var matchedEvents = eventService.findEventsByFilters(filters);
                    matchedEvents.stream()
                            .map(e -> new EventMessage(subscription.getId(), e, objectMapper))
                            .forEach(eventMsg -> {
                                try {
                                    session.sendMessage(new TextMessage(eventMsg.toString()));
                                } catch (IOException e) {
                                    logger.error("❌ Error sending event message: " + e.getMessage(),
                                            e);
                                }
                            });

                    // Send EOSE (End of Stream Event) message
                    var eoseMsg = new EoseMessage(subscription.getId());
                    session.sendMessage(new TextMessage(eoseMsg.toString()));
                    break;

                case CLOSE:
                    // Handle unsubscribe request
                    // payload: [<subscription_id>]
                    var subscriptionId = payload.get(0).asText();

                    // Remove subscription from the session
                    var removedSubscription = subscriptionManager
                            .removeSubscriptionFromSession(session.getId(), subscriptionId);

                    // If the subscription was removed, send a CLOSED message
                    if (removedSubscription.isPresent()) {
                        var closedMsg = new ClosedMessage(removedSubscription.get().getId());
                        session.sendMessage(
                                new TextMessage(objectMapper.writeValueAsString(closedMsg)));
                    }
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
