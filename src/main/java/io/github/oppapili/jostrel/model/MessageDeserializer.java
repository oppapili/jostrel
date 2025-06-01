package io.github.oppapili.jostrel.model;

import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Custom deserializer for the Message class.
 * 
 * <p>
 * This deserializer expects a JSON array where the first element is the message type and the
 * subsequent elements are the payload data.<br>
 * The expected format is an array with at least two elements:
 * {@code [<messageType>, <payloadData>...]}
 * 
 * <p>
 * specification:
 * https://github.com/nostr-protocol/nips/blob/master/01.md#communication-between-clients-and-relays
 * 
 */
public class MessageDeserializer extends JsonDeserializer<Message> {
  /**
   * Deserialize a JSON representation of a Message.
   *
   * @param p the JsonParser to read the JSON content
   * @param ctxt the DeserializationContext
   * @return a Message object constructed from the JSON data
   * @throws IOException if there is an error reading the JSON
   * @throws JsonProcessingException if the JSON format is invalid
   */
  @Override
  public Message deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = p.getCodec().readTree(p);

    if (node.isArray() && node.size() >= 2) {
      var type = MessageType.valueOf(node.get(0).asText());

      var payload = new ArrayList<JsonNode>();
      for (int i = 1; i < node.size(); i++) {
        payload.add(node.get(i));
      }
      return new Message(type, payload);
    }

    throw new JsonProcessingException(
        "Invalid client message format: expected array with type and data payload") {};
  }
}
