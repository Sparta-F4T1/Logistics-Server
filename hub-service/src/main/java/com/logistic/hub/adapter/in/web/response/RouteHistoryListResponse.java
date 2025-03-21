package com.logistic.hub.adapter.in.web.response;

import com.logistic.hub.application.service.dto.RouteHistoryDto;
import java.util.List;
import org.springframework.data.domain.Page;

public record RouteHistoryListResponse(
    List<RouteHistoryDto> content,
    PageInfoResponse pageInfo
) {
  public static RouteHistoryListResponse from(Page<RouteHistoryDto> list) {
    return new RouteHistoryListResponse(list.getContent(), PageInfoResponse.from(list));
  }
}
