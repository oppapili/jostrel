package io.github.oppapili.jostrel.model;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public class EventMessage extends Message {
    public EventMessage(String subscriptionId, Event event, ObjectMapper objectMapper) {
        super(MessageType.EVENT, List.of(JsonNodeFactory.instance.textNode(subscriptionId),
                objectMapper.valueToTree(event)));
    }
}
