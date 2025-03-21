package com.logistic.gps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GpsServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(GpsServiceApplication.class, args);
  }

}
