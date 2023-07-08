/*
 * **********************************************************************************************************
 * Copyright (c) 2018-2021. Sirfsup Inc. and/or its affiliates. (https://www.sirfsup.com) All Rights reserved.
 * File: sirfsup/sirf-shared-exception/SirfErrorToResponseEntityConverter.java
 * Author: Kumar Ashutosh
 * Last Modified: 20/06/21, 5:50 AM
 * **********************************************************************************************************
 */

package skn.lms.exception.handler;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import skn.lms.exception.ErrorMessageMap;
import skn.lms.exception.ErrorResponse;
import skn.lms.exception.ApiException;
import skn.lms.exception.type.Failing;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public interface SirfErrorToResponseEntityConverter {

  default ResponseEntity<?> getErrorResponse(final @NonNull ApiException exception) {

    if (exception.getFailureType() != null) {
      Assert.isAssignable(
          Failing.class,
          exception.getFailureType().getClass(),
          () -> "Provided exception is not of Failing type.");

      final Failing failureType = exception.getFailureType();

      return buildResponseEntity(
          () ->
              ErrorResponse.builder()
                  .code(failureType.getHttpStatus().get().value())
                  .message(failureType.getMessage().get())
                  .timestamp(LocalDateTime.now().toString())
                  .detailMessage(exception.getLocalizedMessage())
                  .build());
    } else if (exception.getStatusCode() != null) {
      return buildResponseEntity(
          () ->
              ErrorResponse.builder()
                  .code(exception.getStatusCode().value())
                  .message(exception.getStatusCode().toString())
                  .timestamp(LocalDateTime.now().toString())
                  .detailMessage(exception.getMessage())
                  .build());
    }
    return buildResponseEntity(
        () ->
            ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Unknown exception. No handler found!")
                .timestamp(LocalDateTime.now().toString())
                .detailMessage(null)
                .build());
  }

  default ResponseEntity<?> getErrorResponse(
      final @NonNull Throwable throwable,
      final @NonNull HttpStatus httpStatus,
      final @NonNull String defaultMessage) {
    String message = ErrorMessageMap.errors.getOrDefault(throwable.getClass(), defaultMessage);

    return buildResponseEntity(
        () ->
            ErrorResponse.builder()
                .code(httpStatus.value())
                .message(message)
                .timestamp(LocalDateTime.now().toString())
                .detailMessage(throwable.getLocalizedMessage())
                .build(),
        httpStatus);
  }

  ResponseEntity<ErrorResponse> getDefaultErrorResponse(final Throwable throwable);

  ResponseEntity<?> buildResponseEntity(Supplier<ErrorResponse> errorSupplier);

  ResponseEntity<?> buildResponseEntity(
      Supplier<ErrorResponse> errorSupplier, HttpStatus httpStatus);
}
