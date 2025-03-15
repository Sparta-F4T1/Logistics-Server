package com.logistic.product.adaptor.in.web;

import com.logistic.common.annotation.WebAdapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.product.adaptor.in.web.mapper.ProductWebMapper;
import com.logistic.product.adaptor.in.web.request.ProductCreateRequest;
import com.logistic.product.adaptor.in.web.response.ProductCreateResponse;
import com.logistic.product.application.port.in.ProductUseCase;
import com.logistic.product.domain.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductWebAdaptor {
  private final ProductUseCase productUseCase;
  private final ProductWebMapper productWebMapper;

  @PostMapping
  public ResponseEntity<ApiResponse<ProductCreateResponse>> createProduct(
      @Valid @RequestBody final ProductCreateRequest request) {
    final Product product = productUseCase.createProduct(productWebMapper.toCreateCommand(request));
    final ApiResponse<ProductCreateResponse> response = ApiResponse.success(productWebMapper.toResponse(product));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}