package com.logistic.hub.adapter.in.web.response;


import org.springframework.data.domain.Page;

public record PageInfoResponse(
    Integer page,
    Integer size,
    Long totalElements,
    Integer totalPages
) {
  public static PageInfoResponse from(Page page) {
    return new PageInfoResponse(
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages()
    );
  }
}