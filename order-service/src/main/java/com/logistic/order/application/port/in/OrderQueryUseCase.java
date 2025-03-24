package com.logistic.order.application.port.in;

import com.logistic.order.application.port.in.query.SearchOrderQuery;
import com.logistic.order.domain.Order;
import org.springframework.data.domain.Page;

public interface OrderQueryUseCase {
  Page<Order> search(SearchOrderQuery query);
}
