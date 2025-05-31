package io.github.oppapili.jostrel.model;

import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;


public class MessageDeserializer extends JsonDeserializer<Message> {
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
