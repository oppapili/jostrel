package io.github.oppapili.jostrel.model;

import java.util.List;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

/**
 * Represents a message indicating that a subscription has been closed in the Nostr protocol.
 * 
 * <p>
 * This message is sent by the server to inform clients that a specific subscription has been
 * terminated. It includes the subscription ID and an optional message providing additional context
 * about the closure.
 * 
 * <p>
 * Specification:
 * https://github.com/nostr-protocol/nips/blob/master/01.md#from-relay-to-client-sending-events-and-notices
 */
public class ClosedMessage extends Message {
    private static final String DEFALUT_MESSAGE = "subscription closed";

    /**
     * Constructs a ClosedMessage with the specified subscription ID and message.
     *
     * @param subscriptionId the ID of the subscription that is closed
     * @param message a message providing additional context about the closure
     */
    public ClosedMessage(String subscriptionId, String message) {
        super(MessageType.CLOSED, List.of(JsonNodeFactory.instance.textNode(subscriptionId),
                JsonNodeFactory.instance.textNode(message)));
    }

    /**
     * Constructs a ClosedMessage with the specified subscription ID.
     *
     * @param subscriptionId the ID of the subscription that is closed
     */
    public ClosedMessage(String subscriptionId) {
        this(subscriptionId, DEFALUT_MESSAGE);
    }
}
