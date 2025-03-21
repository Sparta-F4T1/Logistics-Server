package com.logistic.common.internal.client;

import com.logistic.common.internal.request.ProductClientRequest;
import com.logistic.common.internal.response.ProductClientResponse;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductInternalClient {
  @GetMapping("/internal/v1/products/{productId}")
  ProductClientResponse findProduct(@PathVariable("productId") Long productId,
                                    @RequestBody ProductClientRequest request);

  @GetMapping("/internal/v1/products")
  List<ProductClientResponse> findProductList(@RequestParam List<Long> productIds);

  @PutMapping("/internal/v1/inventory")
  Map<Long, Integer> updateProductInventory(@RequestBody Map<Long, Integer> request);
}
