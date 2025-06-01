package io.github.oppapili.jostrel.model;

/**
 * Represents the different types of messages in the Nostr protocol.
 * 
 * <p>
 * Each message type corresponds to the first element of a message array defined in the NIP-01
 * 
 * <p>
 * Specification:
 * https://github.com/nostr-protocol/nips/blob/master/01.md#communication-between-clients-and-relays
 */
public enum MessageType {
  /** EVENT: Indicate contains event. */
  EVENT,
  /** REQ: Request events and subscribe to new updates. */
  REQ,
  /** CLOSE: Stop previous subscriptions. */
  CLOSE,
  /** OK: Indicate acceptance or denial of an {@code EVENT} message. */
  OK,
  /**
   * EOSE: Indicate the end of stored events and the beginning of events newly received in
   * real-time.
   */
  EOSE,
  /** NOTICE: Send human-readable error messages or other things to clients. */
  NOTICE;
}
