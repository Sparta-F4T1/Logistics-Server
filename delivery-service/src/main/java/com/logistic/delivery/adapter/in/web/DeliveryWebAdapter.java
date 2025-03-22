package com.logistic.delivery.adapter.in.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.delivery.adapter.in.web.mapper.DeliveryWebMapper;
import com.logistic.delivery.adapter.in.web.request.DeliveryCreateRequest;
import com.logistic.delivery.adapter.in.web.request.DeliveryUpdateRequest;
import com.logistic.delivery.adapter.in.web.request.HubDeliveryHistoryUpdateRequest;
import com.logistic.delivery.adapter.in.web.response.DeliveryResponse;
import com.logistic.delivery.adapter.in.web.response.HubDeliveryHistoryResponse;
import com.logistic.delivery.application.port.in.DeliveryUseCase;
import com.logistic.delivery.domain.Delivery;
import com.logistic.delivery.domain.HubDeliveryHistory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryWebAdapter {
  private final DeliveryUseCase deliveryUseCase;
  private final DeliveryWebMapper deliveryWebMapper;

  @PostMapping
  public ResponseEntity<ApiResponse<DeliveryResponse>> createDelivery(
      @Valid @RequestBody DeliveryCreateRequest request
  ) {
    Delivery delivery = deliveryUseCase.createDelivery(deliveryWebMapper.toCreateCommand(request));
    DeliveryResponse response = deliveryWebMapper.toResponse(delivery);
    return ResponseEntity.ok().body(ApiResponse.success(response));
  }

  @PatchMapping("/{deliveryId}")
  public ResponseEntity<ApiResponse<DeliveryResponse>> updateDelivery(
      @PathVariable Long deliveryId,
      @RequestBody DeliveryUpdateRequest request
  ) {
    Delivery delivery = deliveryUseCase.updateDelivery(
        deliveryId,
        deliveryWebMapper.toUpdateCommand(request));
    DeliveryResponse response = deliveryWebMapper.toResponse(delivery);
    return ResponseEntity.ok().body(ApiResponse.success(response));
  }

  @PatchMapping("/{deliveryId}/hubDeliveryHistories")
  public ResponseEntity<ApiResponse<HubDeliveryHistoryResponse>> updateDelivery(
      @PathVariable Long deliveryId,
      @Valid @RequestBody HubDeliveryHistoryUpdateRequest request
  ) {
    HubDeliveryHistory history = deliveryUseCase.updateHubDeliveryHistory(
        deliveryId,
        deliveryWebMapper.toUpdateCommand(request));
    HubDeliveryHistoryResponse response = deliveryWebMapper.toResponse(history);
    return ResponseEntity.ok().body(ApiResponse.success(response));
  }

  @DeleteMapping("/{deliveryId}")
  public ResponseEntity<ApiResponse<?>> deleteDelivery(@PathVariable Long deliveryId)
  {
    deliveryUseCase.deleteDelivery(deliveryWebMapper.toDeleteCommand(deliveryId));
    return ResponseEntity.ok().body(ApiResponse.success());
  }

}
