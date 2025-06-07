package io.github.oppapili.jostrel.model;

import java.util.List;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public class EoseMessage extends Message {
    public EoseMessage(String subscriptionId) {
        super(MessageType.EOSE, List.of(JsonNodeFactory.instance.textNode(subscriptionId)));
    }
}
