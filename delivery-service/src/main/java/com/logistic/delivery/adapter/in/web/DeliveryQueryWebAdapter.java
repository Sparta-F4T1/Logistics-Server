package com.logistic.delivery.adapter.in.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.delivery.adapter.in.web.mapper.DeliveryWebMapper;
import com.logistic.delivery.adapter.in.web.request.DeliverySearchRequest;
import com.logistic.delivery.adapter.in.web.response.DeliveryResponse;
import com.logistic.delivery.application.port.in.DeliveryQueryUseCase;
import com.logistic.delivery.domain.view.DeliveryView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryQueryWebAdapter {
  private final DeliveryQueryUseCase deliveryQueryUseCase;
  private final DeliveryWebMapper deliveryWebMapper;

  @GetMapping("/{deliveryId}")
  public ResponseEntity<ApiResponse<DeliveryResponse>> findDelivery(
      @PathVariable final Long deliveryId
  ) {
    DeliveryView deliveryView = deliveryQueryUseCase.findDelivery(deliveryWebMapper.toQuery(deliveryId));
    return ResponseEntity.ok().body(ApiResponse.success(deliveryWebMapper.toResponse(deliveryView)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<DeliveryResponse>>> searchDeliveries(
      @ModelAttribute final DeliverySearchRequest request,
      @RequestParam(defaultValue = "0") final int page,
      @RequestParam(defaultValue = "10") final int size,
      @RequestParam(defaultValue = "id") final String sortType
  ) {
    Page<DeliveryResponse> response = deliveryQueryUseCase.SearchDelivery(deliveryWebMapper.toQuery(request), page, size, sortType)
        .map(deliveryWebMapper::toResponse);
    return ResponseEntity.ok().body(ApiResponse.success(response));
  }


}
