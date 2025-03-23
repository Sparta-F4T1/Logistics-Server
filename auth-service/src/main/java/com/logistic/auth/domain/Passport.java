package com.logistic.auth.domain;

import com.logistic.auth.domain.vo.UserId;
import com.logistic.auth.domain.vo.UserInfo;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Passport {
  public static final int DEFAULT_SESSION_HOURS = 24;

  private final String sessionId;
  private UserInfo userInfo;
  private LocalDateTime issuedAt;
  private LocalDateTime expiresAt;

  public static Passport create(User user, String sessionId, LocalDateTime now, int sessionHours) {
    UserInfo userInfo = UserInfo.of(user);

    return Passport.builder()
        .sessionId(sessionId)
        .userInfo(userInfo)
        .issuedAt(now)
        .expiresAt(now.plusHours(sessionHours))
        .build();
  }

  public static Passport create(User user, String sessionId, LocalDateTime now) {
    return create(user, sessionId, now, DEFAULT_SESSION_HOURS);
  }

  public boolean isExpired(LocalDateTime currentTime) {
    return currentTime.isAfter(expiresAt);
  }

  public Passport extend(int hours) {
    LocalDateTime now = LocalDateTime.now();
    return Passport.builder()
        .sessionId(this.sessionId)
        .userInfo(this.userInfo)
        .issuedAt(this.issuedAt)
        .expiresAt(now.plusHours(hours))
        .build();
  }

  public Passport newSession(String newSessionId, LocalDateTime now, int hours) {
    return Passport.builder()
        .sessionId(newSessionId)
        .userInfo(this.userInfo)
        .issuedAt(now)
        .expiresAt(now.plusHours(hours))
        .build();
  }

  public UserId getUserId() {
    return userInfo.getUserId();
  }

  public String getRole() {
    return userInfo.getRole();
  }
}