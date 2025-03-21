package com.logistic.user.adapter.in.exception.handler;

import com.logistic.common.handler.AbstractGlobalExceptionHandler;
import com.logistic.common.response.ApiResponse;
import com.logistic.user.domain.exception.ErrorCode;
import com.logistic.user.domain.exception.UserServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(1)
@Log4j2(topic = "[User Service Exception]")
@RestControllerAdvice
public class UserGlobalExceptionHandler extends AbstractGlobalExceptionHandler {

  @ExceptionHandler(UserServiceException.class)
  public ResponseEntity<ApiResponse<Void>> handleAuthServiceException(UserServiceException e) {
    log.error("예외 발생: {}", e.getMessage());
    return createErrorResponse(e.getError());
  }

  private ResponseEntity<ApiResponse<Void>> createErrorResponse(ErrorCode code) {
    ApiResponse<Void> response = ApiResponse.fail(code.getCode(), code.getMessage());
    return ResponseEntity.status(code.getHttpStatus()).body(response);
  }
}