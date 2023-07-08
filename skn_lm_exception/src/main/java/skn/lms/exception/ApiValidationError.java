/*
 * **********************************************************************************************************
 * Copyright (c) 2018-2021. Sirfsup Inc. and/or its affiliates. (https://www.sirfsup.com) All Rights reserved.
 * File: sirfsup/sirf-shared-exception/ApiValidationError.java
 * Author: Kumar Ashutosh
 * Last Modified: 20/06/21, 3:43 AM
 * **********************************************************************************************************
 */

package skn.lms.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Builder
@Deprecated
public class ApiValidationError extends Error {
    @JsonIgnore
    private String objectName;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiValidationError(String objectName) {
        this.objectName = objectName;
    }

    public ApiValidationError(String objectName, String message) {
        this.objectName = objectName;
        this.message = message;
    }

    public ApiValidationError(String message, String field, Object rejectedValue) {
        this.message = message;
        this.field = field;
        this.rejectedValue = rejectedValue;
    }

    /*public ApiValidationError(String objectName, String message, String field, Object rejectedValue) {
        this.objectName = objectName;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }*/

    public static ApiValidationError buildApiValidationError(final String message_, final String field_,
                                                             final String rejectedValue_) {
        return ApiValidationError.builder().message(message_).field(field_).rejectedValue(rejectedValue_).build();
    }


    public static ApiValidationError buildApiValidationError(final String message_, final String field_,
        final Object rejectedValue_) {
    return ApiValidationError.builder()
        .message(message_)
        .field(field_)
        .rejectedValue(rejectedValue_)
        .build();
    }
}
