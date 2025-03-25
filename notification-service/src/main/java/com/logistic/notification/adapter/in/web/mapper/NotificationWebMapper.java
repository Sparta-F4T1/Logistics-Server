package com.logistic.notification.adapter.in.web.mapper;

import com.logistic.notification.adapter.in.web.response.SlackMessageResponse;
import com.logistic.notification.application.port.in.query.SlackMessageFindQuery;
import com.logistic.notification.application.port.in.query.SlackMessageSearchQuery;
import com.logistic.notification.domain.view.SlackMessageView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationWebMapper {
  SlackMessageFindQuery toQuery(Long deliveryId);
  SlackMessageSearchQuery toQuery(String recipient);
  SlackMessageResponse toResponse(SlackMessageView slackMessageView);

}
