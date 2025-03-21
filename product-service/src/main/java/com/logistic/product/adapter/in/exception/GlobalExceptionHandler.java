package com.logistic.product.adapter.in.exception;

import com.logistic.common.handler.AbstractGlobalExceptionHandler;
import com.logistic.common.response.ApiResponse;
import com.logistic.product.domain.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractGlobalExceptionHandler {

  @ExceptionHandler(DomainException.class)
  protected ResponseEntity<ApiResponse<DomainException>> handleDomainException(
      DomainException e) {
    log.error("DomainException", e);
    final ApiResponse<DomainException> response = ApiResponse.fail(e.getCode(), e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }
}
