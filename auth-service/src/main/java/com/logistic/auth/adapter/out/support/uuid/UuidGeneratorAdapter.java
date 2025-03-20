package com.logistic.auth.adapter.out.support.uuid;

import com.logistic.auth.application.port.out.support.uuid.IdGeneratorPort;
import com.logistic.common.annotation.Adapter;
import java.util.UUID;

@Adapter
public class UuidGeneratorAdapter implements IdGeneratorPort {

  @Override
  public String generateUniqueId() {
    return UUID.randomUUID().toString();
  }
}