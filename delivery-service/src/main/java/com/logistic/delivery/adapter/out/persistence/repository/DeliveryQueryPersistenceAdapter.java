package com.logistic.delivery.adapter.out.persistence.repository;

import com.logistic.common.annotation.Adapter;
import com.logistic.delivery.adapter.out.persistence.mapper.DeliveryPersistenceMapper;
import com.logistic.delivery.application.port.out.DeliveryQueryPersistencePort;
import com.logistic.delivery.domain.view.DeliveryView;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@Adapter
@RequiredArgsConstructor
public class DeliveryQueryPersistenceAdapter implements DeliveryQueryPersistencePort {
  private final DeliveryQueryDslRepository deliveryQueryDslRepository;
  private final DeliveryPersistenceMapper deliveryPersistenceMapper;

  @Override
  public Optional<DeliveryView> findDelivery(Long id) {
    return deliveryQueryDslRepository.findDelivery(id).map(deliveryPersistenceMapper::toView);
  }

  @Override
  public Page<DeliveryView> searchDelivery(Optional<Long> orderId,
                                           Optional<Long> departCompanyId,
                                           Optional<Long> arrivalCompanyId,
                                           Optional<String> driverId,
                                           Pageable pageable) {
    return deliveryQueryDslRepository.searchDelivery(
            orderId,
            departCompanyId,
            arrivalCompanyId,
            driverId,
            pageable)
        .map(deliveryPersistenceMapper::toView);
  }
}
