package com.logistic.hub.adapter.in.web.response;

import java.util.List;
import org.springframework.data.domain.Page;

public record HubHistoryListResponse(
    List<HubHistoryResponse> content,
    PageInfoResponse pageInfo
) {

  public static HubHistoryListResponse from(Page<HubHistoryResponse> history) {
    return new HubHistoryListResponse(history.getContent(), PageInfoResponse.from(history));
  }
}
