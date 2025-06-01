package io.github.oppapili.jostrel.model;

import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a message between clients and relays in the Nostr protocol.
 * 
 * <p>
 * This class is used to encapsulate the type of message and its associated payload. The payload is
 * a list of JSON nodes that can represent various data structures.
 * 
 * <p>
 * Specification:
 * https://github.com/nostr-protocol/nips/blob/master/01.md#communication-between-clients-and-relays
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MessageDeserializer.class)
public class Message {
  /** The type of the message, indicating its purpose or content. */
  private MessageType type;

  /** The payload of the message, containing the data associated with the message type. */
  private List<JsonNode> payload;
}
