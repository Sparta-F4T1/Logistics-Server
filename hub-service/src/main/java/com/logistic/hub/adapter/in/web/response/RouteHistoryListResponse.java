package com.logistic.hub.adapter.in.web.response;

import java.util.List;
import org.springframework.data.domain.Page;

public record RouteHistoryListResponse(
    List<RouteHistoryResponse> content,
    PageInfoResponse pageInfo
) {
  public static RouteHistoryListResponse from(Page<RouteHistoryResponse> list) {
    return new RouteHistoryListResponse(list.getContent(), PageInfoResponse.from(list));
  }
}
