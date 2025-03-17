package com.logistic.hub.adaptor.in.web.response;

import com.logistic.hub.domain.HubType;
import com.logistic.hub.domain.vo.Address;

public record HubDetailsResponse(
    Long hubId,
    HubType hubType,
    String hubName,
    Address address
) {
}
