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
    public static final String AI = "lb://AI-SERVICE";
    public static final String COMPANY = "lb://COMPANY-SERVICE";
    public static final String DELIVERY = "lb://DELIVERY-SERVICE";
    public static final String DRIVER = "lb://DRIVER-SERVICE";
    public static final String GPS = "lb://GPS-SERVICE";
    public static final String HUB = "lb://HUB-SERVICE";
    public static final String ORDER = "lb://ORDER-SERVICE";
    public static final String PRODUCT = "lb://PRODUCT-SERVICE";
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

      public static final String AI = API_V1 + "/ai";
      public static final String AI_ALL = AI + "/**";

      public static final String COMPANY = API_V1 + "/companies";
      public static final String COMPANY_ALL = COMPANY + "/**";

      public static final String DELIVERY = API_V1 + "/deliveries";
      public static final String DELIVERY_ALL = DELIVERY + "/**";

      public static final String DRIVER = API_V1 + "/drivers";
      public static final String DRIVER_ALL = DRIVER + "/**";

      public static final String GPS = API_V1 + "/gps";
      public static final String GPS_ALL = GPS + "/**";

      public static final String HUB = API_V1 + "/hubs";
      public static final String HUB_ALL = HUB + "/**";

      public static final String HUB_ROUTES = API_V1 + "/routes";
      public static final String HUB_ROUTES_ALL = HUB_ROUTES + "/**";

      public static final String ORDER = API_V1 + "/orders";
      public static final String ORDER_ALL = ORDER + "/**";

      public static final String PRODUCT = API_V1 + "/products";
      public static final String PRODUCT_ALL = PRODUCT + "/**";
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
    public static final String AI = "ai-service";
    public static final String COMPANY = "company-service";
    public static final String DELIVERY = "delivery-service";
    public static final String DRIVER = "driver-service";
    public static final String GPS = "gps-service";
    public static final String HUB = "hub-service";
    public static final String ORDER = "order-service";
    public static final String PRODUCT = "product-service";
  }
}