package com.logistic.product.application.service;

import com.logistic.product.application.port.out.ProductLockPort;
import com.logistic.product.domain.event.StockLockReleaseEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventListener {
  private final ProductLockPort lockPort;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
  public void unLock(final StockLockReleaseEvent event) {
    List<Long> productIds = event.getProductIds();
    log.info("[락 해제 이벤트 수신] event: {}, productIds: {}", event, event.getProductIds().toString());
    lockPort.unLock(productIds);
  }
}
