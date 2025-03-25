package com.logistic.notification.application.service;

import com.logistic.notification.application.port.in.NotificationQueryUseCase;
import com.logistic.notification.application.port.in.query.SlackMessageFindQuery;
import com.logistic.notification.application.port.in.query.SlackMessageSearchQuery;
import com.logistic.notification.application.port.out.SlackMessageQueryPersistencePort;
import com.logistic.notification.domain.view.SlackMessageView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationQueryService implements NotificationQueryUseCase {

  private final SlackMessageQueryPersistencePort persistencePort;

  @Override
  public SlackMessageView findSlackMessage(SlackMessageFindQuery query) {
    return persistencePort.findDelivery(query.deliveryId()).orElseThrow();
  }

  @Override
  public Page<SlackMessageView> searchSlackMessage(
      SlackMessageSearchQuery query,
      int page,
      int size,
      String sortType
  ) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortType));
    return persistencePort.searchDelivery(query.recipient(), pageable);
  }
}
