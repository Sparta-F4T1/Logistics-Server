package com.logistic.gps.adapter.out.external.response;

import java.util.List;

public record RouteDto(
    List<TraoptimalDto> traoptimal
) {
}
