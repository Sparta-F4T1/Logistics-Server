package com.logistic.notification.adapter.out.persistence.repository;

import com.logistic.common.annotation.Adapter;
import com.logistic.notification.adapter.out.persistence.repository.mapper.SlackMessagePersistenceMapper;
import com.logistic.notification.application.port.out.SlackMessageQueryPersistencePort;
import com.logistic.notification.domain.view.SlackMessageView;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@Adapter
@RequiredArgsConstructor
public class SlackMessageQueryPersistenceAdapter implements SlackMessageQueryPersistencePort {
  private final SlackMessageQueryDslRepository repository;
  private final SlackMessagePersistenceMapper mapper;

  @Override
  public Optional<SlackMessageView> findDelivery(Long id) {
    return repository.findSlackMessage(id).map(mapper::toView);
  }

  @Override
  public Page<SlackMessageView> searchDelivery(String recipient, Pageable pageable) {
    return repository.searchSlackMessage(recipient, pageable).map(mapper::toView);
  }
}
