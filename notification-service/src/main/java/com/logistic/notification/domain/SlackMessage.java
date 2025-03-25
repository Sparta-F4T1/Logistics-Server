package com.logistic.notification.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlackMessage {
  private Long id;
  private String recipient;
  private String text;
  private Boolean isSent;
  private LocalDateTime sentAt;

  public static SlackMessage create(
      String recipient,
      String text,
      Boolean isSent,
      LocalDateTime sentAt
  ){
    return SlackMessage.builder()
        .recipient(recipient)
        .text(text)
        .isSent(isSent)
        .sentAt(sentAt)
        .build();
  }
}
