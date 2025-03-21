package com.logistic.hub.adapter.in.web.response;

import com.logistic.hub.application.service.dto.HubHistoryDto;
import java.util.List;
import org.springframework.data.domain.Page;

public record HubHistoryListResponse(
    List<HubHistoryDto> content,
    PageInfoResponse pageInfo
) {

  public static HubHistoryListResponse from(Page<HubHistoryDto> history) {
    return new HubHistoryListResponse(history.getContent(), PageInfoResponse.from(history));
  }
}
