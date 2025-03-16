package com.logistic.product.adaptor.in.web;

import com.logistic.common.annotation.WebAdapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.product.adaptor.in.web.mapper.ProductWebMapper;
import com.logistic.product.adaptor.in.web.request.ProductSearchRequest;
import com.logistic.product.adaptor.in.web.response.ProductResponse;
import com.logistic.product.application.port.in.ProductQueryUseCase;
import com.logistic.product.domain.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductQueryAdaptor {
  private final ProductQueryUseCase productQueryUseCase;
  private final ProductWebMapper productWebMapper;

  @GetMapping("/{productId}")
  public ResponseEntity<ApiResponse<ProductResponse>> findProduct(
      @PathVariable final Long productId) {
    final Product product = productQueryUseCase.findProduct(productWebMapper.toFindQuery(productId));
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(productWebMapper.toProductResponse(product)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<ProductResponse>>> search(
      @Valid @ModelAttribute final ProductSearchRequest request,
      @PageableDefault final Pageable pageable) {
    final Page<ProductResponse> response = productQueryUseCase.search(request, pageable)
        .map(productWebMapper::toProductResponse);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(response));
  }
}
