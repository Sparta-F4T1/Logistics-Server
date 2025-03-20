package com.logistic.product.adapter.in.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.request.ProductClientRequest;
import com.logistic.common.internal.response.ProductClientResponse;
import com.logistic.product.adapter.in.internal.mapper.ProductInternalMapper;
import com.logistic.product.application.port.in.ProductCommandUseCase;
import com.logistic.product.application.port.in.ProductQueryUseCase;
import com.logistic.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/products")
public class ProductServiceAdapter {
  private final ProductQueryUseCase queryUseCase;
  private final ProductCommandUseCase commandUseCase;
  private final ProductInternalMapper mapper;

  @PostMapping("{productId}/stock/add")
  public ProductClientResponse addStock(@PathVariable Long productId, @RequestParam Integer quantity) {
    final Product product = commandUseCase.addStock(mapper.toAddCommand(productId, quantity));
    return mapper.toResponse(product);
  }

  @PostMapping("{productId}/stock/decrease")
  public ProductClientResponse decreaseStock(@PathVariable Long productId, @RequestParam Integer quantity) {
    final Product product = commandUseCase.decreaseStock(
        mapper.toDecreaseCommand(productId, quantity));
    return mapper.toResponse(product);
  }

  @GetMapping("/{productId}")
  public ProductClientResponse findProduct(@PathVariable Long productId, @RequestBody ProductClientRequest request) {
    Product product = queryUseCase.findProduct(mapper.toFindQuery(productId, request.passport()));
    return mapper.toResponse(product);
  }

}
