package com.logistic.order.adapter.in.handler;

import com.logistic.common.handler.AbstractGlobalExceptionHandler;
import com.logistic.common.response.ApiResponse;
import com.logistic.common.response.code.ErrorCode;
import com.logistic.order.domain.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractGlobalExceptionHandler {
  @ExceptionHandler(BaseException.class)
  protected ResponseEntity<ApiResponse<BaseException>> handleException(BaseException exception) {
    log.error(exception.getClass().getSimpleName(), exception);
    ErrorCode code = exception.getCode();
    final ApiResponse<BaseException> response = ApiResponse.fail(code.getCode(), code.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }
}
