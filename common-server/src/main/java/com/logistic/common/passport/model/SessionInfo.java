package com.logistic.common.passport.model;

import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionInfo implements Serializable {
  private static final long serialVersionUID = 1L;
  private String sessionId;
  private Instant issuedAt;
  private Instant expiresAt;
}