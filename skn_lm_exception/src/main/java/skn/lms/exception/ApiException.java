/*
 * **********************************************************************************************************
 * Copyright (c) 2018-2021. Sirfsup Inc. and/or its affiliates. (https://www.sirfsup.com) All Rights reserved.
 * File: sirfsup/sirf-shared-exception/SirfApiException.java
 * Author: Kumar Ashutosh
 * Last Modified: 20/06/21, 4:15 AM
 * **********************************************************************************************************
 */

package skn.lms.exception;

import skn.lms.exception.type.Failing;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public class ApiException extends ResponseStatusException {

  private Failing failureCause;
  private Throwable fallbackException;

  public ApiException(HttpStatus status, Failing failuretype, Throwable fallbackException) {
    super(status);
    this.fallbackException = fallbackException;
    this.failureCause = failuretype;
  }

  public ApiException(HttpStatus status, @Nullable String reason) {
    super(status, reason);
  }

  public ApiException(Failing failuretype, @Nullable String reason) {
    super(failuretype.getHttpStatus().get(), reason);
    this.failureCause = failuretype;
  }

  public ApiException(Failing failuretype) {
    this(failuretype, failuretype.getMessage().get());
  }

  public ApiException(
      HttpStatus status, String reason, Failing failuretype, Throwable fallbackException) {
    super(status, reason);
    this.fallbackException = fallbackException;
    this.failureCause = failuretype;
  }

  public ApiException(
      HttpStatus status,
      String reason,
      Throwable cause,
      Failing failuretype,
      Throwable fallbackException) {
    super(status, reason, cause);
    this.fallbackException = fallbackException;
    this.failureCause = failuretype;
  }

  public ApiException(Failing failuretype, String message, Throwable fallbackException) {
    super(failuretype.getHttpStatus().get(), message, fallbackException);
    this.fallbackException = fallbackException;
    this.failureCause = failuretype;
  }

  public ApiException(Failing failuretype, Throwable fallbackException) {
    super(failuretype.getHttpStatus().get(), failuretype.getMessage().get(), fallbackException);
    this.fallbackException = fallbackException;
    this.failureCause = failuretype;
  }

  public Failing getFailureType() {
    return this.failureCause;
  }

  @Override
  public String getMessage() {
    return NestedExceptionUtils.buildMessage(getReason(), getCause());
  }
}
