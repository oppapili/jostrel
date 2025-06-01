package io.github.oppapili.jostrel.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Represents a subscription in the Nostr protocol.
 */
@Data
@Builder
@AllArgsConstructor
public class Subscription {
  /**
   * Arbitrary, non-empty string of max length 64 chars. It represents a subscription per
   * connection. Relays MUST manage. Ids are not globally unique.
   */
  private final String id;
  private final List<Filter> filters;
}
