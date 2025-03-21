package com.logistic.order.application.port.out;

import com.logistic.order.domain.vo.OrderProduct;
import java.util.List;
import java.util.Map;

public interface OrderInternalPort {

  Map<Long, Integer> updateProductInventory(List<OrderProduct> orderProducts);
}
