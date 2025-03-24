package com.logistic.order.adapter.out.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.request.ProductClientRequest;
import com.logistic.order.adapter.out.internal.client.ProductFeignClient;
import com.logistic.order.application.port.out.OrderInternalPort;
import com.logistic.order.domain.vo.OrderProduct;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class OrderInternalAdapter implements OrderInternalPort {
  private final ProductFeignClient productFeignClient;

  public void updateProductInventory(List<OrderProduct> orderProducts){
    ProductClientRequest request = new ProductClientRequest(orderProducts.stream()
        .collect(Collectors.toMap(OrderProduct::getProductId, OrderProduct::getQuantity)));
    productFeignClient.updateStock(request);
  }
}
