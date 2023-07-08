/*
 * **********************************************************************************************************
 * Copyright (c) 2018-2021. Sirfsup Inc. and/or its affiliates. (https://www.sirfsup.com) All Rights reserved.
 * File: sirfsup/sirf-shared-exception/ErrorResponse.java
 * Author: Kumar Ashutosh
 * Last Modified: 16/06/21, 2:35 AM
 * **********************************************************************************************************
 */

package skn.lms.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
@Getter
@Setter
public class ErrorResponse {

  private int code;
  private String message;
  private String detailMessage;
  private String timestamp;

  @Singular(value = "addError")
  private List<String> errors;

  public static ErrorResponse of(final HttpStatus httpStatus, final String message) {
    return ErrorResponse.builder()
        .code(httpStatus.value())
        .message(httpStatus.getReasonPhrase())
        .detailMessage(message)
        .timestamp(LocalDateTime.now().toString())
        .build();
  }
}
