package com.logistic.ai.application.port.out;

import java.time.LocalDateTime;

public interface ChatGPTPort {
  String getResponse(LocalDateTime orderCreatedAt,
                     Integer totalHubDeliveryTime,
                     String arrivalHubAddress,
                     String arrivalCompanyAddress);
}