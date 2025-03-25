package com.logistic.notification.application.port.in;

import com.logistic.notification.application.port.in.query.SlackMessageFindQuery;
import com.logistic.notification.application.port.in.query.SlackMessageSearchQuery;
import com.logistic.notification.domain.view.SlackMessageView;
import org.springframework.data.domain.Page;

public interface NotificationQueryUseCase {
  SlackMessageView findSlackMessage(SlackMessageFindQuery query);
  Page<SlackMessageView> searchSlackMessage(SlackMessageSearchQuery query, int page, int size, String sortType);
}
