package com.logistic.notification.application.port.out;

import com.logistic.notification.domain.view.SlackMessageView;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SlackMessageQueryPersistencePort {
  Optional<SlackMessageView> findDelivery(Long id);
  Page<SlackMessageView> searchDelivery(String recipient, Pageable pageable);
}
