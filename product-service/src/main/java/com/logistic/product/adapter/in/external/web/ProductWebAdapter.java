package com.logistic.product.adapter.in.external.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.passport.annotation.WithPassport;
import com.logistic.common.passport.model.Passport;
import com.logistic.common.response.ApiResponse;
import com.logistic.product.adapter.in.external.web.mapper.ProductWebMapper;
import com.logistic.product.adapter.in.external.web.request.CreateProductRequest;
import com.logistic.product.adapter.in.external.web.request.SearchProductRequest;
import com.logistic.product.adapter.in.external.web.request.UpdateProductRequest;
import com.logistic.product.adapter.in.external.web.response.CommandProductResponse;
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
  private final ProductWebMapper mapper;
  private final ProductQueryUseCase productQueryUseCase;
  private final ProductCommandUseCase productCommandUseCase;

  @PostMapping
  public ResponseEntity<ApiResponse<CommandProductResponse>> createProduct(
      @Valid @RequestBody final CreateProductRequest request,
      @WithPassport final Passport passport) {
    final Product product = productCommandUseCase.createProduct(mapper.toCreateCommand(request, passport));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(mapper.toProductResponse(product)));
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ApiResponse<CommandProductResponse>> updateProduct(
      @PathVariable final Long productId,
      @RequestBody final UpdateProductRequest request,
      @WithPassport final Passport passport) {
    final Product product = productCommandUseCase.updateProduct(
        mapper.toUpdateInfoCommand(productId, request, passport));
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(mapper.toProductResponse(product)));
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<ApiResponse<Void>> deleteProduct(
      @PathVariable final Long productId,
      @WithPassport final Passport passport) {
    productCommandUseCase.deleteProduct(mapper.toDeleteCommand(productId, passport));
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(null));
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ApiResponse<CommandProductResponse>> findProduct(
      @PathVariable final Long productId,
      @WithPassport final Passport passport) {
    final Product product = productQueryUseCase.findProduct(mapper.toFindQuery(productId, passport));
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(mapper.toProductResponse(product)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<CommandProductResponse>>> search(
      @Valid @ModelAttribute final SearchProductRequest request,
      @PageableDefault final Pageable pageable,
      @WithPassport final Passport passport) {
    final Page<CommandProductResponse> response = productQueryUseCase.search(
            mapper.toSearchQuery(request, pageable, passport))
        .map(mapper::toProductResponse);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(response));
  }
}