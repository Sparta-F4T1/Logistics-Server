package com.logistic.product.adapter.in.external.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.product.adapter.in.external.web.mapper.ProductWebMapper;
import com.logistic.product.adapter.in.external.web.request.CreateProductRequest;
import com.logistic.product.adapter.in.external.web.request.SearchProductRequest;
import com.logistic.product.adapter.in.external.web.request.UpdateProductRequest;
import com.logistic.product.adapter.in.external.web.response.FindProductResponse;
import com.logistic.product.application.port.in.ProductCommandUseCase;
import com.logistic.product.application.port.in.ProductQueryUseCase;
import com.logistic.product.domain.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductWebAdapter {
  private final ProductCommandUseCase productCommandUseCase;
  private final ProductWebMapper productWebMapper;
  private final ProductQueryUseCase productQueryUseCase;

  @PostMapping
  public ResponseEntity<ApiResponse<FindProductResponse>> createProduct(
      @Valid @RequestBody final CreateProductRequest request) {
    final Product product = productCommandUseCase.createProduct(productWebMapper.toCreateCommand(request));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(productWebMapper.toProductResponse(product)));
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ApiResponse<FindProductResponse>> updateProduct(
      @PathVariable final Long productId,
      @RequestBody final UpdateProductRequest request) {
    final Product product = productCommandUseCase.updateProduct(
        productWebMapper.toUpdateInfoCommand(productId, request));
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(productWebMapper.toProductResponse(product)));
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<ApiResponse<Void>> deleteProduct(
      @PathVariable final Long productId) {
    productCommandUseCase.deleteProduct(productWebMapper.toDeleteCommand(productId));
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(null));
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ApiResponse<FindProductResponse>> findProduct(
      @PathVariable final Long productId) {
    final Product product = productQueryUseCase.findProduct(productWebMapper.toFindQuery(productId));
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(productWebMapper.toProductResponse(product)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<FindProductResponse>>> search(
      @Valid @ModelAttribute final SearchProductRequest request,
      @PageableDefault final Pageable pageable) {
    final Page<FindProductResponse> response = productQueryUseCase.search(
            productWebMapper.toSearchQuery(request, pageable))
        .map(productWebMapper::toProductResponse);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(response));
  }
}