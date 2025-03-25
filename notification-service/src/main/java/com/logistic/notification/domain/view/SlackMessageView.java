package com.logistic.notification.domain.view;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlackMessageView {
  private Long id;
  private String recipient;
  private String text;
  private Boolean isSent;
  private LocalDateTime sentAt;

  public static SlackMessageView create(
      String recipient,
      String text,
      Boolean isSent,
      LocalDateTime sentAt
  ){
    return SlackMessageView.builder()
        .recipient(recipient)
        .text(text)
        .isSent(isSent)
        .sentAt(sentAt)
        .build();
  }
}
