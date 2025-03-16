package com.logistic.product.adaptor.in.internal;

import com.logistic.common.annotation.WebAdapter;
import com.logistic.product.adaptor.in.internal.mapper.ProductInternalMapper;
import com.logistic.product.adaptor.in.internal.response.ProductDto;
import com.logistic.product.application.port.in.ProductUseCase;
import com.logistic.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@WebAdapter
@RequestMapping("/internal/v1/products")
@RequiredArgsConstructor
public class ProductInternalAdaptor {
  private final ProductUseCase productUseCase;
  private final ProductInternalMapper productInternalMapper;

  @PostMapping("{productId}/stock/add")
  public ProductDto addStock(@PathVariable Long productId, @RequestParam Integer quantity) {
    final Product product = productUseCase.addStock(productInternalMapper.toStockAddCommand(productId, quantity));
    return productInternalMapper.toProductDto(product);
  }

  @PostMapping("{productId}/stock/decrease")
  public ProductDto decreaseStock(@PathVariable Long productId, @RequestParam Integer quantity) {
    final Product product = productUseCase.decreaseStock(
        productInternalMapper.toStockDecreaseCommand(productId, quantity));
    return productInternalMapper.toProductDto(product);
  }
}
