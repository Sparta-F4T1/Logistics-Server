package com.logistic.order.adapter.in.external.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.order.adapter.in.external.web.mapper.OrderWebMapper;
import com.logistic.order.adapter.in.external.web.request.SearchOrderRequest;
import com.logistic.order.adapter.in.external.web.response.SearchOrderResponse;
import com.logistic.order.application.port.in.OrderQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderQueryAdapter {
  private final OrderQueryUseCase orderQueryUseCase;
  private final OrderWebMapper orderWebMapper;

  @GetMapping
  public ResponseEntity<ApiResponse<PagedModel<SearchOrderResponse>>> searchOrder(
      @ModelAttribute SearchOrderRequest searchOrderRequest,
      @PageableDefault Pageable pageable) {
    Page<SearchOrderResponse> orders = orderQueryUseCase
        .search(orderWebMapper.toSearchQuery(searchOrderRequest, pageable))
        .map(orderWebMapper::toSearchOrderResponse);

    ApiResponse<PagedModel<SearchOrderResponse>> response = ApiResponse.success(new PagedModel<>(orders));
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
  }
}
