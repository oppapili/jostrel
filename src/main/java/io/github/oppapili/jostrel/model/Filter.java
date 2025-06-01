package io.github.oppapili.jostrel.model;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a filter for querying events in the Nostr protocol.
 * 
 * <p>
 * This class encapsulates the criteria used to filter events, including event IDs, authors, kinds,
 * tags, and time constraints.
 * 
 * <p>
 * Specification:
 * https://github.com/nostr-protocol/nips/blob/master/01.md#communication-between-clients-and-relays
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter {

  /** List of event ids. */
  private List<String> ids;

  /** List of lowercase pubkeys, the pubkey of an event must be one of these. */
  private List<String> authors;

  /** List of a kind numbers. */
  private List<Integer> kinds;

  /**
   * The keys are tag names, and the values are lists of tag values.
   * 
   * <p>
   * Tag name is a #single-letter (a-zA-Z).<br>
   * 
   * <pre>
   * {"#p": ["pubkey1", "pubkey2"], "#e": ["eventId1", "eventId2"]}
   * </pre>
   */
  @JsonIgnoreProperties({"ids", "authors", "kinds", "since", "until", "limit"})
  @JsonAnySetter
  private Map<String, List<String>> tags;

  /** Minimum creation unix timestamp to filter. */
  private Long since;

  /** Maximum creation unix timestamp to filter. */
  private Long until;

  /** maximum number of events relays SHOULD return in the initial query. */
  private Integer limit;
}
