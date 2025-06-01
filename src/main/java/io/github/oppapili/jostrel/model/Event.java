package io.github.oppapili.jostrel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an event in the Nostr protocol.
 * 
 * <p>
 * This class encapsulates the details of an event, including its ID, public key of the author,
 * creation timestamp, kind of event, content, tags, and signature.
 * 
 * <p>
 * Specification: https://github.com/nostr-protocol/nips/blob/master/01.md#events-and-signatures
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
  /** 32-bytes lowercase hex-encoded sha256 of the serialized event data. */
  private String id;

  /** 32-bytes lowercase hex-encoded public key of the event creator. */
  private String pubkey;

  /** Unix timestamp in seconds. */
  private long created_at;

  /** Integer between 0 and 65535. */
  private int kind;

  /** Arbitrary strings. */
  private String[] tags;

  /** The content of the event. */
  private String content;

  /**
   * 64-bytes lowercase hex of the signature of the sha256 hash of the serialized event data, which
   * is the same as the {@link Event#id} field.
   */
  private String sig;
}
