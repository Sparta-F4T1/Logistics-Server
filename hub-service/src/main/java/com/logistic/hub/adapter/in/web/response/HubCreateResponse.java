package com.logistic.hub.adapter.in.web.response;

import com.logistic.hub.domain.vo.Address;

public record HubCreateResponse(
    Long hubId,
    String hubName,
    Address address
) {
}
