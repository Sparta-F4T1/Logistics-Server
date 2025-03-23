package com.logistic.auth.adapter.out.support.resource;

import com.logistic.auth.application.port.out.support.resource.ResourceExtractorPort;
import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
import com.logistic.auth.domain.vo.ActionType;
import com.logistic.auth.domain.vo.ResourceType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class HttpResourceExtractorAdapter implements ResourceExtractorPort {
  private static final Pattern RESOURCE_PATTERN = Pattern.compile("/api/v(\\d+)/([^/]+)(?:/(\\d+))?");

  @Override
  public ResourceType extractResourceType(String path) {
    Matcher matcher = RESOURCE_PATTERN.matcher(path);
    if (matcher.find()) {
      String pluralResourceName = matcher.group(2).toUpperCase();

      String singularResourceName = convertToSingular(pluralResourceName);
      try {
        return ResourceType.valueOf(singularResourceName);
      } catch (IllegalArgumentException e) {
        throw AuthServiceException.auth(AuthServiceErrorCode.INVALID_RESOURCE_TYPE,
            ": " + singularResourceName);
      }
    }
    throw AuthServiceException.auth(AuthServiceErrorCode.INVALID_RESOURCE_PATH,
        ": " + path);
  }

  private String convertToSingular(String pluralResourceName) {
    switch (pluralResourceName) {
      case "COMPANIES":
        return "COMPANY";
      case "USERS":
        return "USER";
      case "PRODUCTS":
        return "PRODUCT";
      case "HUBS":
        return "HUB";
      case "DELIVERIES":
        return "DELIVERY";
      case "HUBDELIVERYHISTORIES":
        return "HUB_DELIVERY_HISTORY";
      case "DRIVERS":
        return "DRIVER";
      case "ORDERS":
        return "ORDER";
      case "SLACK":
        return "SLACK_MESSAGE";
      default:
        if (pluralResourceName.endsWith("IES")) {
          return pluralResourceName.substring(0, pluralResourceName.length() - 3) + "Y";
        } else if (pluralResourceName.endsWith("ES")) {
          return pluralResourceName.substring(0, pluralResourceName.length() - 2);
        } else if (pluralResourceName.endsWith("S")) {
          return pluralResourceName.substring(0, pluralResourceName.length() - 1);
        }
        return pluralResourceName;
    }
  }

  @Override
  public ActionType extractActionType(String httpMethod) {
    switch (httpMethod.toUpperCase()) {
      case "GET":
        return ActionType.READ;
      case "POST":
        return ActionType.CREATE;
      case "PUT":
      case "PATCH":
        return ActionType.UPDATE;
      case "DELETE":
        return ActionType.DELETE;
      default:
        throw AuthServiceException.auth(AuthServiceErrorCode.UNSUPPORTED_HTTP_METHOD,
            ": " + httpMethod);
    }
  }
}