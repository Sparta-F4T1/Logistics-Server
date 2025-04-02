package com.logistic.driver.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DomainEventEnvelop<T extends DomainEvent> {
  private T event;
  private UUID eventId;
  private LocalDateTime createdAt;
  private String eventType;
  private String source;

  public static <T extends DomainEvent> DomainEventEnvelop<T> of(T event, String source) {
    return new DomainEventEnvelop<>(
        event, UUID.randomUUID(), LocalDateTime.now(), event.getClass().getTypeName(), source
    );
  }

  public static <T extends DomainEvent> DomainEventEnvelop<T> valueOf(
      T event, String uuid, LocalDateTime createdAt, String eventType, String source
  ) {
    return new DomainEventEnvelop<>(
        event, UUID.fromString(uuid), createdAt, eventType, source);
  }
}
