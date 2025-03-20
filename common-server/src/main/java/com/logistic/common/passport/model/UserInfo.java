package com.logistic.common.passport.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
  private static final long serialVersionUID = 1L;
  private String userId;
  private String role;
  private Long targetId;
}