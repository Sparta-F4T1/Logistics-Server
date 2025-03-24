package com.logistic.order.application.port.out;

import com.logistic.order.domain.vo.OrderProduct;
import java.util.List;

public interface OrderInternalPort {
  void updateProductInventory(List<OrderProduct> orderProducts);

}
