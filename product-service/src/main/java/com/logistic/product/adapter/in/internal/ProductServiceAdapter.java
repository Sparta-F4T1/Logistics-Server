package com.logistic.product.adapter.in.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.request.ProductClientRequest;
import com.logistic.common.internal.response.ProductClientResponse;
import com.logistic.common.passport.annotation.WithPassport;
import com.logistic.common.passport.model.Passport;
import com.logistic.product.adapter.in.internal.mapper.ProductInternalMapper;
import com.logistic.product.application.port.in.ProductCommandUseCase;
import com.logistic.product.application.port.in.ProductQueryUseCase;
import com.logistic.product.domain.Product;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/products")
public class ProductServiceAdapter {
  private final ProductInternalMapper mapper;
  private final ProductQueryUseCase queryUseCase;
  private final ProductCommandUseCase commandUseCase;

  @GetMapping("/{productId}")
  public ProductClientResponse findProduct(@PathVariable Long productId,
                                           @WithPassport Passport passport) {
    final Product product = queryUseCase.findProduct(mapper.toFindQuery(productId, passport));
    return mapper.toResponse(product);
  }

  @GetMapping
  public List<ProductClientResponse> findProductList(@ModelAttribute ProductClientRequest request,
                                                     @WithPassport Passport passport) {
    final List<Product> productList = queryUseCase.findProductList(mapper.toListQuery(request, passport));
    return productList.stream().map(mapper::toResponse).toList();
  }

  @PutMapping("/stock")
  public List<ProductClientResponse> decreaseStock(@RequestBody ProductClientRequest request,
                                                   @WithPassport Passport passport) {
    List<Product> productList = commandUseCase.decreaseStock(mapper.toUpdateCommand(request, passport));
    return productList.stream().map(mapper::toResponse).toList();
  }

}
