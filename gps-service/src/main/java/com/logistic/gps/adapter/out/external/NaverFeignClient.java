package com.logistic.gps.adapter.out.external;

import com.logistic.gps.adapter.out.external.response.NaverDirectionResponse;
import com.logistic.gps.adapter.out.external.response.NaverGeoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverClient", url = "https://naveropenapi.apigw.ntruss.com")
public interface NaverFeignClient {


  @GetMapping("/map-geocode/v2/geocode")
  NaverGeoResponse getGpsInfo(
      @RequestParam String query,
      @RequestHeader("x-ncp-apigw-api-key-id") String clientId,
      @RequestHeader("x-ncp-apigw-api-key") String clientSecret,
      @RequestHeader("accept") String accept
  );

  @GetMapping("/map-direction/v1/driving")
  NaverDirectionResponse getDistanceInfo(
      @RequestParam String start,
      @RequestParam String goal,
      @RequestHeader("x-ncp-apigw-api-key-id") String clientId,
      @RequestHeader("x-ncp-apigw-api-key") String clientSecret,
      @RequestHeader("accept") String accept
  );
}
