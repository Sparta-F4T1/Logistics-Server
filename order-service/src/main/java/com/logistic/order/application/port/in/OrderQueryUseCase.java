package com.logistic.order.application.port.in;

import com.logistic.order.application.port.in.query.SearchOrderQuery;
import com.logistic.order.domain.Order;
import org.springframework.data.web.PagedModel;

public interface OrderQueryUseCase {
  PagedModel<Order> search(SearchOrderQuery query);
}
