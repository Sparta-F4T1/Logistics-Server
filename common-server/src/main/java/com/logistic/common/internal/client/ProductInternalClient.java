package com.logistic.common.internal.client;

import com.logistic.common.internal.request.ProductClientRequest;
import com.logistic.common.internal.response.ProductClientResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductInternalClient {
  @GetMapping("/internal/v1/products/{productId}")
  ProductClientResponse findProduct(@PathVariable("productId") Long productId);

  @PutMapping("/internal/v1/products/stock")
  void updateStock(@RequestBody ProductClientRequest request);
}
