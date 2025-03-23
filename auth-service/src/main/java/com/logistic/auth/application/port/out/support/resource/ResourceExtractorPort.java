package com.logistic.auth.application.port.out.support.resource;

import com.logistic.auth.domain.vo.ActionType;
import com.logistic.auth.domain.vo.ResourceType;

public interface ResourceExtractorPort {
  ResourceType extractResourceType(String path);

  ActionType extractActionType(String httpMethod);
}