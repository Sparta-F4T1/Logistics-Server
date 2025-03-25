package com.logistic.notification.adapter.in.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.notification.adapter.in.web.mapper.NotificationWebMapper;
import com.logistic.notification.adapter.in.web.response.SlackMessageResponse;
import com.logistic.notification.application.port.in.NotificationQueryUseCase;
import com.logistic.notification.domain.view.SlackMessageView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationQueryWebAdapter {

  private final NotificationQueryUseCase queryUseCase;
  private final NotificationWebMapper mapper;

  @GetMapping("/{notificationId}")
  public ResponseEntity<ApiResponse<SlackMessageResponse>> findSlackMessage(
      @PathVariable final Long notificationId
  ) {
    SlackMessageView view = queryUseCase.findSlackMessage(mapper.toQuery(notificationId));
    return ResponseEntity.ok().body(ApiResponse.success(mapper.toResponse(view)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<SlackMessageResponse>>> searchDeliveries(
      @RequestParam final String recipient,
      @RequestParam(defaultValue = "0") final int page,
      @RequestParam(defaultValue = "10") final int size,
      @RequestParam(defaultValue = "id") final String sortType
  ) {
    Page<SlackMessageResponse> response = queryUseCase.searchSlackMessage(mapper.toQuery(recipient), page, size, sortType)
        .map(mapper::toResponse);
    return ResponseEntity.ok().body(ApiResponse.success(response));
  }


}
