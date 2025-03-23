package com.logistic.ai.adapter.out.external.ai.dto;

import java.util.List;

public record GPTResponse(
    List<Choice> choices
) {
  public record Choice(int index, Message message) {
  }
}
