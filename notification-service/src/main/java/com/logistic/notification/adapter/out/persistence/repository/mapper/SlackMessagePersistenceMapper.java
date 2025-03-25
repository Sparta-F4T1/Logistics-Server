package com.logistic.notification.adapter.out.persistence.repository.mapper;

import com.logistic.notification.adapter.out.persistence.SlackMessageEntity;
import com.logistic.notification.domain.view.SlackMessageView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SlackMessagePersistenceMapper {
  SlackMessageView toView(SlackMessageEntity entity);
}