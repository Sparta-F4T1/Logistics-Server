package com.logistic.ai.adapter.out.external.ai.dto;

import java.util.Objects;

public record Message(
    String role,
    String content
) {
  public Message {
    Objects.requireNonNull(role);
    Objects.requireNonNull(content);
  }
}
