package com.logistic.order.application.port.out;

import com.logistic.order.domain.Order;
import com.logistic.order.domain.vo.OrderProduct;
import java.util.List;


public interface MessagePort {
  void sendCreateOrder(Order order, String slackEmail);

  void sendCancelOrder(List<OrderProduct> orderProducts);
}