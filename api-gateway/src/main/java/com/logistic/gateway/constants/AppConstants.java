package com.logistic.gateway.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {
  public static class Filter {
    public static final String TOKEN_ATTR = "token";
    public static final String ERROR_MISSING_TOKEN = "토큰이 존재하지 않습니다.";
  }

  public static class ServiceUri {
    public static final String AUTH = "lb://AUTH-SERVICE";
    public static final String USER = "lb://USER-SERVICE";
  }

  public static class ApiPath {
    private static final String API_V1 = "/api/v1";
    private static final String INTERNAL_V1 = "/internal/v1";

    public static class Api {
      public static final String AUTH = API_V1 + "/auth";
      public static final String AUTH_ALL = AUTH + "/**";
      public static final String AUTH_LOGIN = AUTH + "/login";
      public static final String AUTH_LOGOUT = AUTH + "/logout";
      public static final String AUTH_REFRESH = AUTH + "/refresh";

      public static final String USERS = API_V1 + "/users";
      public static final String USERS_ALL = USERS + "/**";
      public static final String USERS_SIGNUP = USERS;
    }

    public static class Internal {
      public static final String AUTH = INTERNAL_V1 + "/auth";
      public static final String AUTH_VERIFY_TOKEN = AUTH + "/verify-token";
      public static final String AUTH_VALIDATE_ACCESS = AUTH + "/access/validate";
    }
  }

  public static class RouteId {
    public static final String AUTH = "auth-service";
    public static final String USER = "user-service";
  }
}