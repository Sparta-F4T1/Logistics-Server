package com.logistic.hub.adaptor.in.exception;

import com.logistic.common.handler.AbstractGlobalExceptionHandler;
import com.logistic.common.response.ApiResponse;
import com.logistic.common.response.code.ErrorCode;
import com.logistic.hub.domain.exception.HubAlreadyDeletedException;
import com.logistic.hub.domain.exception.HubNotFoundException;
import com.logistic.hub.domain.exception.HubSameSelectionException;
import com.logistic.hub.domain.exception.RouteAlreadyDeletedException;
import com.logistic.hub.domain.exception.RouteCalculateFailedException;
import com.logistic.hub.domain.exception.RouteCreateFailedException;
import com.logistic.hub.domain.exception.RouteInvalidInfoException;
import com.logistic.hub.domain.exception.RouteNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(1)
@Slf4j
@RestControllerAdvice
public class HubExceptionHandler extends AbstractGlobalExceptionHandler {

  @ExceptionHandler(HubNotFoundException.class)
  protected ResponseEntity<ApiResponse<HubNotFoundException>> handleException(HubNotFoundException e) {
    log.error("handleException", e);
    ErrorCode code = ErrorCode.NOT_FOUND;
    final ApiResponse<HubNotFoundException> response = ApiResponse.fail(code.getCode(), e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(HubAlreadyDeletedException.class)
  protected ResponseEntity<ApiResponse<HubAlreadyDeletedException>> handleException(HubAlreadyDeletedException e) {
    log.error("handleException", e);
    ErrorCode code = ErrorCode.INVALID_INPUT_VALUE;
    final ApiResponse<HubAlreadyDeletedException> response = ApiResponse.fail(code.getCode(), e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(HubSameSelectionException.class)
  protected ResponseEntity<ApiResponse<HubSameSelectionException>> handleException(HubSameSelectionException e) {
    log.error("handleException", e);
    ErrorCode code = ErrorCode.INVALID_INPUT_VALUE;
    final ApiResponse<HubSameSelectionException> response = ApiResponse.fail(code.getCode(), e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(RouteAlreadyDeletedException.class)
  protected ResponseEntity<ApiResponse<RouteAlreadyDeletedException>> handleException(RouteAlreadyDeletedException e) {
    log.error("handleException", e);
    ErrorCode code = ErrorCode.INVALID_INPUT_VALUE;
    final ApiResponse<RouteAlreadyDeletedException> response = ApiResponse.fail(code.getCode(), e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(RouteCalculateFailedException.class)
  protected ResponseEntity<ApiResponse<RouteCalculateFailedException>> handleException(
      RouteCalculateFailedException e) {
    log.error("handleException", e);
    ErrorCode code = ErrorCode.INTERNAL_SERVER_ERROR;
    final ApiResponse<RouteCalculateFailedException> response = ApiResponse.fail(code.getCode(), e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @ExceptionHandler(RouteCreateFailedException.class)
  protected ResponseEntity<ApiResponse<RouteCreateFailedException>> handleException(RouteCreateFailedException e) {
    log.error("handleException", e);
    ErrorCode code = ErrorCode.INTERNAL_SERVER_ERROR;
    final ApiResponse<RouteCreateFailedException> response = ApiResponse.fail(code.getCode(), e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @ExceptionHandler(RouteNotFoundException.class)
  protected ResponseEntity<ApiResponse<RouteNotFoundException>> handleException(RouteNotFoundException e) {
    log.error("handleException", e);
    ErrorCode code = ErrorCode.NOT_FOUND;
    final ApiResponse<RouteNotFoundException> response = ApiResponse.fail(code.getCode(), e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(RouteInvalidInfoException.class)
  protected ResponseEntity<ApiResponse<RouteInvalidInfoException>> handleException(RouteInvalidInfoException e) {
    log.error("handleException", e);
    ErrorCode code = ErrorCode.INTERNAL_SERVER_ERROR;
    final ApiResponse<RouteInvalidInfoException> response = ApiResponse.fail(code.getCode(), e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

}
