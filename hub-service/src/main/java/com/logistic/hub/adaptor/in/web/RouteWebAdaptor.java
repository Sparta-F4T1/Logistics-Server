package com.logistic.hub.adaptor.in.web;

import com.logistic.common.annotation.WebAdapter;
import com.logistic.hub.application.port.in.RouteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubRoutes")
public class RouteWebAdaptor {
  private final RouteUseCase routeUseCase;


}
