package com.logistic.common.internal.client;

import com.logistic.common.internal.request.NotificationClientRequest;
import com.logistic.common.internal.response.NotificationClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface NotificationInternalClient {
  @GetMapping("/internal/v1/notifications/{notificationId}")
  NotificationClientResponse findNotification(@PathVariable("notificationId") Long notificationId,
                                              @RequestBody NotificationClientRequest request);

  @GetMapping("/internal/v1/notifications")
  List<NotificationClientResponse> findNotificationList(@RequestBody NotificationClientRequest request);
}
