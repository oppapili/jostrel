package io.github.oppapili.jostrel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
  private String id;
  private String pubkey;
  private long created_at;
  private int kind;
  private String content;
  private String[] tags;
  private String sig;
}
