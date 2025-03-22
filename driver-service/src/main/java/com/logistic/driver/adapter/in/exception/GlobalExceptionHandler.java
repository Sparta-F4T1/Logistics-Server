package com.logistic.driver.adapter.in.exception;

import com.logistic.common.handler.AbstractGlobalExceptionHandler;
import com.logistic.common.response.ApiResponse;
import com.logistic.driver.domain.exception.CustomBadRequestException;
import com.logistic.driver.domain.exception.CustomForbiddenException;
import com.logistic.driver.domain.exception.CustomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractGlobalExceptionHandler {

  @ExceptionHandler(CustomBadRequestException.class)
  protected ResponseEntity<ApiResponse<CustomBadRequestException>> handleBadRequestException(
      CustomBadRequestException e) {
    log.error("{} occurred: {}", e.getClass().getSimpleName(), e.getMessage(), e);
    final ApiResponse<CustomBadRequestException> response = ApiResponse.fail(e.getCode(), e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(CustomNotFoundException.class)
  protected ResponseEntity<ApiResponse<CustomNotFoundException>> handleNotFoundException(
      CustomNotFoundException e) {
    log.error("{} occurred: {}", e.getClass().getSimpleName(), e.getMessage(), e);
    final ApiResponse<CustomNotFoundException> response = ApiResponse.fail(e.getCode(), e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(CustomForbiddenException.class)
  protected ResponseEntity<ApiResponse<CustomForbiddenException>> handleForbiddenException(
      CustomForbiddenException e) {
    log.error("{} occurred: {}", e.getClass().getSimpleName(), e.getMessage(), e);
    final ApiResponse<CustomForbiddenException> response = ApiResponse.fail(e.getCode(), e.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }
}
