package com.logistic.delivery.adaptor.in.web;

import com.logistic.common.annotation.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {
}
