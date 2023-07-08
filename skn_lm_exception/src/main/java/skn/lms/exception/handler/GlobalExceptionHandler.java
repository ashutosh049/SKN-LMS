/*
 * **********************************************************************************************************
 * Copyright (c) 2018-2021. Sirfsup Inc. and/or its affiliates. (https://www.sirfsup.com) All Rights reserved.
 * File: sirfsup/sirf-shared-exception/SirfApiExceptionHandler.java
 * Author: Kumar Ashutosh
 * Last Modified: 20/06/21, 5:50 AM
 * **********************************************************************************************************
 */

package skn.lms.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import skn.lms.exception.ErrorResponse;

import java.util.function.Supplier;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
    implements SirfErrorToResponseEntityConverter {

  public String DEFAULT_INTERNAL_SERVER_ERROR_MESSAGE = "Something went wrong at our end!";

  public ResponseEntity<ErrorResponse> getDefaultErrorResponse(final Throwable throwable) {
    log.error(throwable.getLocalizedMessage());
    throwable.printStackTrace();
    return new ResponseEntity<>(
        ErrorResponse.builder()
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message(DEFAULT_INTERNAL_SERVER_ERROR_MESSAGE)
            .addError(DEFAULT_INTERNAL_SERVER_ERROR_MESSAGE)
            .build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public ResponseEntity<Object> buildResponseEntity(Supplier<ErrorResponse> responseSupplier) {
    return buildResponseEntity(
        responseSupplier, HttpStatus.valueOf(responseSupplier.get().getCode()));
  }

  public ResponseEntity<Object> buildResponseEntity(
      Supplier<ErrorResponse> responseSupplier, HttpStatus httpStatus) {
    return new ResponseEntity<>(responseSupplier.get(), httpStatus);
  }
}
