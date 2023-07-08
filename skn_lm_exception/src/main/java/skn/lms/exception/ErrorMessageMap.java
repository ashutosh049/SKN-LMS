/*
 * **********************************************************************************************************
 * Copyright (c) 2018-2021. Sirfsup Inc. and/or its affiliates. (https://www.sirfsup.com) All Rights reserved.
 * File: sirfsup/sirf-shared-exception/ErrorMessageMap.java
 * Author: Kumar Ashutosh
 * Last Modified: 20/06/21, 4:17 AM
 * **********************************************************************************************************
 */

package skn.lms.exception;

import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ErrorMessageMap {
  public static Map<Class, String> errors = new HashMap<>();

  /*static {
     registerError(AuthenticationException.class, () -> "Unauthorized");
     registerError(BadCredentialsException.class, () -> "Invalid credentials");
    registerError(JwtExpiredTokenException.class, () -> "Token expired");
    registerError(InvalidJwtAuthenticationTokenException.class, () -> "Invalid token");
    registerError(TokenEncryptionException.class, () -> "Unable to parse token");
  }*/

  public static void registerError(Class clazz, Supplier<String> messageSupplier) {
    errors.put(clazz, nullSafeGet(messageSupplier));
  }

  @Nullable
  private static String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
    return messageSupplier != null ? (String) messageSupplier.get() : null;
  }
}
