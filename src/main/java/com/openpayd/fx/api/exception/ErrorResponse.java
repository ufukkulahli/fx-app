package com.openpayd.fx.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ErrorResponse {
    public final String error;
    public final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public final String cause;

    public ErrorResponse(String error, String message, String cause) {
        this.error = error;
        this.message = message;
        this.cause = cause;
    }
}
