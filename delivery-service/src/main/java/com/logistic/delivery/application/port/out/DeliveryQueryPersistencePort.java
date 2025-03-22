package com.logistic.delivery.application.port.out;

import com.logistic.delivery.domain.view.DeliveryView;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryQueryPersistencePort {
  Optional<DeliveryView> findDelivery(Long id);
  Page<DeliveryView> searchDelivery(Optional<Long> orderId,
                                    Optional<Long> departCompanyId,
                                    Optional<Long> arrivalCompanyId,
                                    Optional<String> driverId,
                                    Pageable pageable);
}
