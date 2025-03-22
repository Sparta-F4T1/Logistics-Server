package com.logistic.delivery.application.port.in;

import com.logistic.delivery.application.port.in.query.DeliveryFindQuery;
import com.logistic.delivery.application.port.in.query.DeliverySearchQuery;
import com.logistic.delivery.domain.view.DeliveryView;
import org.springframework.data.domain.Page;

public interface DeliveryQueryUseCase {
  DeliveryView findDelivery(DeliveryFindQuery query);
  Page<DeliveryView> SearchDelivery(DeliverySearchQuery query, int page, int size, String sortType);
}
