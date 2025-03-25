package com.logistic.notification.adapter.out.persistence.repository;

import com.logistic.common.annotation.Adapter;
import com.logistic.notification.adapter.out.persistence.SlackMessageEntity;
import com.logistic.notification.application.port.out.SlackMessagePersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
@RequiredArgsConstructor
public class SlackMessagePersistenceAdapter implements SlackMessagePersistencePort {
  private final SlackMessageJpaRepository deliveryJpaRepository;

  @Override
  public void create(String driverID, String message) {
    deliveryJpaRepository.save(SlackMessageEntity.builder()
        .recipient(driverID)
        .text(message)
        .isSent(false)
        .build());
  }
}
