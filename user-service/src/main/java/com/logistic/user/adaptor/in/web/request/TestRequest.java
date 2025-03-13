package com.logistic.user.adaptor.in.web.request;

import java.util.Objects;

public record TestRequest(
     String id
) {
  public TestRequest {
    Objects.requireNonNull(id);
  }
}
